(ns hyperphor.way.cheatmap
  (:require [hyperphor.way.vega :as v]
            [hyperphor.way.cluster :as cluster]
            [org.candelbio.multitool.math :as um]
            [hyperphor.way.web-utils :as wu]
            )
  )

;;; TODO row/col annotations w colors and scales, see examples
;;; TODO more parameterization incl option to have tree on right side

;;; Generates the spec for a single tree 
(defn tree
  [cluster-data left?]
  {:type "group"
   :style "cell"
   :data 
   [{:name "links"
     :source cluster-data
     :transform
     [{:type "treelinks"}
      {:type "linkpath"
       :orient (if left? "horizontal" "vertical")
       :shape "orthogonal"}]}]
   :encoding {:width {:signal (if left? "dend_width" "hm_width")} 
              :height {:signal (if left? "hm_height" "dend_width")}
              :strokeWidth {:value 0}}
   :marks [{:type "path"
            :from {:data "links"}
            :encode {:enter
                     {:path {:field "path"}
                      :stroke {:value "#666"}}}}]
   })

;;; Generates TWO data specs (for full tree, and filtered to leaves)
(defn tree-data-spec
  [name clusters left?]
  [{:name name
    :values clusters
    :transform
    [{:type "stratify" :key "id" :parentKey "parent"}
     {:type "tree"
      :method "cluster"
      :size [{:signal (if left? "hm_height" "hm_width")} ;;  
             {:signal "dend_width"}]
      :as (if left?
            ["y" "x" "depth" "children"]
            ["x" "y" "depth" "children"])}]}
   {:name (str name "-leaf")
    :source name
    :transform [{:type "filter" :expr "datum.children == 0"}]}]
  )

;;; TODO → Multitool
(defn concatv
  "Conj a value to the front (left) of vector. Not performant"
  [& args]
  (vec (apply concat args)))

(defn spec
  [data h-field v-field value-field h-clusters v-clusters]
  (let [hsize (count (distinct (map h-field data))) ;wanted to this in vega but circularities are interfering
        vsize (count (distinct (map v-field data)))]
    {:description "A clustered heatmap with side-dendrograms"
     :$schema "https://vega.github.io/schema/vega/v5.json"
     :layout {:align "each"
              :columns 2}
     :data (concatv
            [{:name "hm"
              :values data}]
            (when h-clusters (tree-data-spec "htree" h-clusters true))
            (when v-clusters (tree-data-spec "vtree" v-clusters false)))
     :scales
     ;; Note: min is because sorting apparently requires an aggregation? And there's no pickone
     [{:name "sx" :type "band"
       :domain (if v-clusters
                 {:data "vtree-leaf" :field "id" :sort {:field "x" :op "min"}}
                 {:data "hm" :field v-field :sort true})
       :range {:step 20} } 
      {:name "sy" :type "band"
       :domain (if h-clusters
                 {:data "htree-leaf" :field "id" :sort {:field "y" :op "min"}}
                 {:data "hm" :field h-field :sort true}
                 )
       :range {:step 20}} 
      {:name "color"
       :type "linear"
       :range {:scheme "BlueOrange"}
       :domain {:data "hm" :field value-field}
       }]
     :signals
     [{:name "hm_width" :value (* 20 vsize)}
      {:name "hm_height" :value (* 20 hsize)}
      {:name "dend_width" :value 50}]
     :padding 5
     :marks
     [
      ;; Upper-left Empty quadrant
      {:type :group                       
       :style :cell
       :encode {:enter {:width (if h-clusters {:signal "dend_width"} {:value 0})
                        :height (if v-clusters {:signal "dend_width"} {:value 0})
                        :strokeWidth {:value 0}}}}

      ;; column tree
      (if v-clusters
        (tree "vtree" false)
        {:type "group"
         :style "cell"
         :encoding {:width {:signal "hm_width"} 
                    :height {:value 0}
                    :strokeWidth {:value 0}}
         })

      ;; row tree
      (if h-clusters
        (tree "htree" true)
        {:type "group"
         :style "cell"
         :encoding {:width {:value 0}
                    :height {:signal "hm_height"} 
                    :strokeWidth {:value 0}}
         })

      ;; actual heatmapmap 
      {:type "group"
       :name "heatmap"
       :style "cell"
       :encode {:update {:width {:signal "hm_width"}
                         :height {:signal "hm_height"}}}
       :axes
       [{:orient :right :scale :sy :domain false :title (wu/humanize h-field)} 
        {:orient :bottom :scale :sx :labelAngle 90 :labelAlign "left" :labelBaseline :middle :domain false :title (wu/humanize v-field)}]

       :legends
       [{:fill :color
         :type :gradient
         :title (wu/humanize value-field)
         :titleOrient "bottom"
         :gradientLength {:signal "hm_height / 2"} ;TODO not always right
         }]

       :marks
       [{:type "rect"
         :from {:data "hm"}
         :encode
         {:enter
          {:y {:field h-field :scale "sy"}
           :x {:field v-field :scale "sx"}
           :width {:value 19} :height {:value 19}
           :fill {:field value-field :scale "color"}
           }}}
        ]
       }
      ]}))

(defn aggregate
  [data dim-cols agg-col agg-fn]
  (let [agg-fn (case agg-fn
                 :sum #(apply + %)
                 :count count
                 :mean um/mean)]
    (->> data
         (filter #(every? % (cons agg-col dim-cols)))
         (group-by (apply juxt dim-cols))
         (map (fn [[dims rows]]
                (assoc (zipmap dim-cols dims)
                       agg-col
                       (agg-fn (keep agg-col rows))
                       )))
         )))

;;; This is the top-level call. Takes data and three field designators, does clustering on both dimensions
;;; and outputs a heatmap with dendrograms
(defn heatmap
  [data row-field col-field value-field & {:keys [aggregate-fn cluster-rows? cluster-cols?]}]
  (when (and data row-field col-field value-field)
    (let [data (aggregate data [row-field col-field] value-field (or aggregate-fn :sum))
          cluster-l (when cluster-rows?
                      (cluster/cluster-data data row-field col-field value-field ))
          cluster-u (when cluster-cols?
                      (cluster/cluster-data data col-field row-field value-field ))]
      [v/vega-view (spec data row-field col-field value-field cluster-l cluster-u) []])
    ))


