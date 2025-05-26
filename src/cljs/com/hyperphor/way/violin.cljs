(ns com.hyperphor.way.violin
  (:require [com.hyperphor.way.vega :as v]
            [com.hyperphor.way.cutils :as cu]
            [com.hyperphor.way.web-utils :as wu]
            )
  )

;;; TODO update this and example from BRUCE

(defn spec
  [data dim feature options]
  (let [dim (name dim)
        #_ scale #_ (interpret-scale @(rf/subscribe [:param :features :scale]))] ;TODO wee fui/ref below
    {:description "A violin plot example showing distributions for pengiun body mass.",
     :$schema "https://vega.github.io/schema/vega/v5.json",
     :width 700,
     :signals
     [{:name "blobWidth", :value 200, :bind {:input :range, :min 100, :max 1000}} ;controls fatness of violins  
      {:name "blobSpace" :value 750 :bind {:input :range, :min 100, :max 2000}}
      {:name "height", :update "blobSpace"}
      {:name "points", :value true, :bind {:input "checkbox"}}
      {:name "boxes" :value true :bind {:input "checkbox"}}
      {:name "violins" :value true :bind  {:input "checkbox"}}
      {:name "jitter" :value 50 :bind {:input :range, :min 0, :max 200}}
      {:name "trim", :value true, :bind {:input "checkbox"}}

      ;; TODO this didn't work, so going out of Vega
      #_ {"name" "xscales", "value" "linear" "bind"  {"input" "select" "options" ["linear" "log10" "log2" "sqrt"]}}
      {:name "bandwidth", :value 0, :bind {:input "range", :min 0, :max 1.0E-4, :step 1.0E-6}}],
     :data
     [{:name "source", :values data}
      {:name "density",
       :source "source",
       :transform
       [{:type "kde",                   ; Kernel Density Estimation, see https://vega.github.io/vega/docs/transforms/kde/
         :field "feature_value",
         :groupby [dim],
         :bandwidth {:signal "bandwidth"},
         :resolve "shared"
         #_ :extent #_ {:signal "trim ? null : [0.0003, 0.0005]"}}]}
      {:name "stats",
       :source "source",
       :transform
       [{:type "aggregate",
         :groupby [dim],
         :fields ["feature_value" "feature_value" "feature_value" "feature_value" "feature_value"],
         :ops ["min" "q1" "median" "q3" "max"],
         :as ["min" "q1" "median" "q3" "max"]}]}]

     :config {:axisBand {:bandPosition 1, :tickExtra true, :tickOffset 0}},
     :axes
     [{:orient "bottom", :scale "xscale", :zindex 1, :title (cu/humanize feature)} ;TODO want metacluster in this
      {:orient "left", :scale "layout", :tickCount 5, :zindex 1}],

     :scales
     [{:name "layout",
       :type "band",
       :range "height",
       :domain {:data "source", :field dim},
       :paddingOuter 0.5}
      {:name "xscale",
       :range "width",
       :round true,
       :domain {:data "source", :field "feature_value"},
       :nice true}
      {:name "hscale",
       :type "linear",
       :range [0 {:signal "blobWidth"}],
       :domain {:data "density", :field "density"}}
      {:name "color", :type "ordinal", :domain {:data "source", :field dim}, :range "category"}],
     :padding 5,
     :marks
     [{:type "group",
       :from {:facet {:data "density", :name "violin", :groupby dim}},
       :encode
       {:update
        {:yc {:scale "layout", :field dim, :band 0.5},
         :height {:signal "blobWidth"},
         :width {:signal "width"}}},
       :data
       [{:name "summary",
         :source "stats",
         :transform [{:type "filter", :expr (wu/js-format "datum.%s === parent.%s" dim dim)}]}],
       :marks
       [

        ;; Violins
        {:type "area",
         :from {:data "violin"},
         :encode
         {:enter {:fill {:scale "color", :field {:parent dim}}},
          :update
          {:x {:scale "xscale", :field "value"},
           :yc {:signal "blobWidth / 2"},
           :height {:scale "hscale", :field "density"}
           :opacity {:signal "violins ? 1 : 0"}}}}

        ;; Points
        {:type "symbol",
         :from {:data "source"},
         :encode
         {:enter {:fill "black", :y {:value 0}},
          :update
          {:stroke {:value "#000000"},
           :fill {:value "#000000"},
           :size {:value 25},
           :z {:value 1000000},
           :yc {:signal "blobWidth / 2 + jitter*(random() - 0.5)"}, ;should scale with fatness
           :strokeWidth {:value 1},
           :opacity {:signal "points ? 0.3 : 0"},
           :shape {:value "circle"},
           :x {:scale "xscale", :field "feature_value"}}}}

        ;; Box outline

        {:type "rect",
         :from {:data "summary"},
         :encode
         {:enter {:stroke {:value "black"},
                  :height {:value 2}
                  :cornerRadius {:value 4}
                  },
          :update
          {:x {:scale "xscale", :field "q1"},
           :x2 {:scale "xscale", :field "q3"},
           :height {:signal "blobWidth / 5"}
           :yc {:signal "blobWidth / 2"}
           :opacity {:signal "boxes ? 1 : 0"}
           :fill {:signal (str "violins ? '' :  scale('color', datum." dim ")")} 
           }
          }}



        ;; Midpoint
       {:type "rect",
         :from {:data "summary"},
         :encode
         {:enter {:fill {:value "black"},
                  :width {:value 2},
                  :height {:value 8}},
          :update {:x {:scale "xscale", :field "median"},
                   :yc {:signal "blobWidth / 2"}
                   :opacity {:signal "boxes ? 1 : 0"}}}}

        ;; Whisker 
        {:type "rect",                 
         :from {:data "summary"},
         :encode
         {:enter {:fill {:value "black"},
                  :height {:value 2}},
          :update {:x {:scale "xscale", :field "min"},
                   :x2 {:scale "xscale", :field "max"}
                   :yc {:signal "blobWidth / 2"}
                   :height {:value 2}
                   :opacity {:signal "boxes ? 1 : 0"}
                   }}}

        ]}],
     }))

(def default-options {})

(defn violin
  [data row-field feature-field options]
  (let [options (merge default-options options)
        {:keys []} options]
    (when (and data row-field feature-field)
      [v/vega-view (spec data row-field feature-field options) []])))

