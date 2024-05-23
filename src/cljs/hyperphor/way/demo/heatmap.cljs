(ns hyperphor.way.demo.heatmap
  (:require [hyperphor.way.cheatmap :as ch]))

(def data2
  '({:gene "WNT2", :sample "508", :value 4.69455368790816}
    {:gene "WNT2", :sample "520", :value 4.28285130191303}
    {:gene "WNT2", :sample "521", :value 1.2370000288886}
    {:gene "WNT2", :sample "512", :value 5.98372036486048}
    {:gene "WNT2", :sample "509", :value 1.3328580234688}
    {:gene "WNT2", :sample "517", :value -0.165525201539909}
    {:gene "WNT2", :sample "516", :value 2.11057837942801}
    {:gene "WNT2", :sample "513", :value 2.89864835625364}
    {:gene "DNM1", :sample "508", :value 6.18073526217034}
    {:gene "DNM1", :sample "520", :value 6.28530033555224}
    {:gene "DNM1", :sample "521", :value 4.48895537322577}
    {:gene "DNM1", :sample "512", :value 5.66002401990528}
    {:gene "DNM1", :sample "509", :value 4.44196514365529}
    {:gene "DNM1", :sample "517", :value 3.9603755075091}
    {:gene "DNM1", :sample "516", :value 5.8002922874965}
    {:gene "DNM1", :sample "513", :value 3.98151281042034}
    {:gene "ZBTB16", :sample "508", :value -1.86352251820081}
    {:gene "ZBTB16", :sample "520", :value -0.515040727317009}
    {:gene "ZBTB16", :sample "521", :value 5.7791730037569}
    {:gene "ZBTB16", :sample "512", :value -1.7771519191856}
    {:gene "ZBTB16", :sample "509", :value 5.25796696285121}
    {:gene "ZBTB16", :sample "517", :value 4.28308662399116}
    {:gene "ZBTB16", :sample "516", :value -2.93197221657965}
    {:gene "ZBTB16", :sample "513", :value 4.9022228878453}
    {:gene "DUSP1", :sample "508", :value 4.93655107030217}
    {:gene "DUSP1", :sample "520", :value 5.14324586287326}
    {:gene "DUSP1", :sample "521", :value 8.39670934427623}
    {:gene "DUSP1", :sample "512", :value 5.60756813037785}
    {:gene "DUSP1", :sample "509", :value 8.01907429882787}
    {:gene "DUSP1", :sample "517", :value 7.72298976466619}
    {:gene "DUSP1", :sample "516", :value 5.02834170722824}
    {:gene "DUSP1", :sample "513", :value 8.30219557783798}
    {:gene "HIF3A", :sample "508", :value 1.0139912580994}
    {:gene "HIF3A", :sample "520", :value 2.25012442319609}
    {:gene "HIF3A", :sample "521", :value 4.79058092806672}
    {:gene "HIF3A", :sample "512", :value 1.57525031122943}
    {:gene "HIF3A", :sample "509", :value 3.37445673080234}
    {:gene "HIF3A", :sample "517", :value 2.83358793070328}
    {:gene "HIF3A", :sample "516", :value 0.492887784795945}
    {:gene "HIF3A", :sample "513", :value 4.15474019163534}
    {:gene "MT2A", :sample "508", :value 6.24851399016412}
    {:gene "MT2A", :sample "520", :value 5.91636282515681}
    {:gene "MT2A", :sample "521", :value 7.93349175824132}
    {:gene "MT2A", :sample "512", :value 5.78285535349592}
    {:gene "MT2A", :sample "509", :value 8.27698758640279}
    {:gene "MT2A", :sample "517", :value 8.20158550590639}
    {:gene "MT2A", :sample "516", :value 5.75838967893522}
    {:gene "MT2A", :sample "513", :value 8.07084582202555}
    {:gene "FGD4", :sample "508", :value 4.13241331926396}
    {:gene "FGD4", :sample "520", :value 4.58981850877196}
    {:gene "FGD4", :sample "521", :value 7.03908171742351}
    {:gene "FGD4", :sample "512", :value 4.2162201879756}
    {:gene "FGD4", :sample "509", :value 6.2395775164949}
    {:gene "FGD4", :sample "517", :value 6.30382156424754}
    {:gene "FGD4", :sample "516", :value 4.31271009913433}
    {:gene "FGD4", :sample "513", :value 6.50205756195231}
    {:gene "PRSS35", :sample "508", :value 3.93001559453838}
    {:gene "PRSS35", :sample "520", :value 4.48262215247243}
    {:gene "PRSS35", :sample "521", :value 1.607251411321}
    {:gene "PRSS35", :sample "512", :value 5.18907269381948}
    {:gene "PRSS35", :sample "509", :value 1.14344085971871}
    {:gene "PRSS35", :sample "517", :value 2.06814189565329}
    {:gene "PRSS35", :sample "516", :value 4.84830664593644}
    {:gene "PRSS35", :sample "513", :value 2.64982582436249}
    {:gene "ADAM12", :sample "508", :value 6.67785831073959}
    {:gene "ADAM12", :sample "520", :value 5.81732874866786}
    {:gene "ADAM12", :sample "521", :value 4.14377753052124}
    {:gene "ADAM12", :sample "512", :value 6.98451044997398}
    {:gene "ADAM12", :sample "509", :value 4.82250784199589}
    {:gene "ADAM12", :sample "517", :value 5.10896456918063}
    {:gene "ADAM12", :sample "516", :value 6.86031250858483}
    {:gene "ADAM12", :sample "513", :value 4.96455744842029}
    {:gene "SPARCL1", :sample "508", :value 1.55470970310097}
    {:gene "SPARCL1", :sample "520", :value 2.03488498060457}
    {:gene "SPARCL1", :sample "521", :value 6.71983276215129}
    {:gene "SPARCL1", :sample "512", :value 2.02259555555733}
    {:gene "SPARCL1", :sample "509", :value 6.72069644167212}
    {:gene "SPARCL1", :sample "517", :value 6.00586264525223}
    {:gene "SPARCL1", :sample "516", :value 2.02834001111556}
    {:gene "SPARCL1", :sample "513", :value 6.34935521091851}
    {:gene "ACSS1", :sample "508", :value 3.71483726631926}
    {:gene "ACSS1", :sample "520", :value 4.23313747152727}
    {:gene "ACSS1", :sample "521", :value 5.99510828523058}
    {:gene "ACSS1", :sample "512", :value 3.86937676723286}
    {:gene "ACSS1", :sample "509", :value 5.56550314244328}
    {:gene "ACSS1", :sample "517", :value 4.88790377026001}
    {:gene "ACSS1", :sample "516", :value 2.91630176912867}
    {:gene "ACSS1", :sample "513", :value 5.79920433839758}
    {:gene "TIMP4", :sample "508", :value 0.598793673732585}
    {:gene "TIMP4", :sample "520", :value 0.819552014211164}
    {:gene "TIMP4", :sample "521", :value 3.63867920029566}
    {:gene "TIMP4", :sample "512", :value 0.580523267025356}
    {:gene "TIMP4", :sample "509", :value 3.76820277073184}
    {:gene "TIMP4", :sample "517", :value 4.25311956115208}
    {:gene "TIMP4", :sample "516", :value 1.5275170040646}
    {:gene "TIMP4", :sample "513", :value 3.37516813739973}
    {:gene "STEAP2", :sample "508", :value 5.90927303326086}
    {:gene "STEAP2", :sample "520", :value 5.94039233384028}
    {:gene "STEAP2", :sample "521", :value 7.93029052160909}
    {:gene "STEAP2", :sample "512", :value 6.0463676874266}
    {:gene "STEAP2", :sample "509", :value 7.72325041735153}
    {:gene "STEAP2", :sample "517", :value 7.2693879518966}
    {:gene "STEAP2", :sample "516", :value 5.34380759907602}
    {:gene "STEAP2", :sample "513", :value 8.15485426186916}
    {:gene "PDPN", :sample "508", :value 5.35753249909343}
    {:gene "PDPN", :sample "520", :value 4.18969108667715}
    {:gene "PDPN", :sample "521", :value 5.99287820478704}
    {:gene "PDPN", :sample "512", :value 4.35562719096266}
    {:gene "PDPN", :sample "509", :value 7.20677629425236}
    {:gene "PDPN", :sample "517", :value 5.02574217372951}
    {:gene "PDPN", :sample "516", :value 3.12195409198714}
    {:gene "PDPN", :sample "513", :value 6.28395625499249}
    {:gene "NEXN", :sample "508", :value 6.38633543860892}
    {:gene "NEXN", :sample "520", :value 6.85334055833747}
    {:gene "NEXN", :sample "521", :value 8.71889675737361}
    {:gene "NEXN", :sample "512", :value 6.79475336433913}
    {:gene "NEXN", :sample "509", :value 8.60998436036047}
    {:gene "NEXN", :sample "517", :value 8.42466113213688}
    {:gene "NEXN", :sample "516", :value 6.47359951916285}
    {:gene "NEXN", :sample "513", :value 8.8347306816487}
    {:gene "DNAJB4", :sample "508", :value 5.03276842874731}
    {:gene "DNAJB4", :sample "520", :value 5.06014990916259}
    {:gene "DNAJB4", :sample "521", :value 6.54135805502078}
    {:gene "DNAJB4", :sample "512", :value 5.2842361428814}
    {:gene "DNAJB4", :sample "509", :value 6.5087197277283}
    {:gene "DNAJB4", :sample "517", :value 6.70020334810133}
    {:gene "DNAJB4", :sample "516", :value 5.2410588772593}
    {:gene "DNAJB4", :sample "513", :value 6.87435015921954}
    {:gene "VCAM1", :sample "508", :value 5.39422051601125}
    {:gene "VCAM1", :sample "520", :value 5.03804927591473}
    {:gene "VCAM1", :sample "521", :value 0.694381107656567}
    {:gene "VCAM1", :sample "512", :value 5.58242033138669}
    {:gene "VCAM1", :sample "509", :value 1.71977948787564}
    {:gene "VCAM1", :sample "517", :value 2.53465016621424}
    {:gene "VCAM1", :sample "516", :value 5.76263513289002}
    {:gene "VCAM1", :sample "513", :value 1.96834777623529}
    {:gene "CACNB2", :sample "508", :value 1.91233417188757}
    {:gene "CACNB2", :sample "520", :value 2.31037269750291}
    {:gene "CACNB2", :sample "521", :value 5.60468736900362}
    {:gene "CACNB2", :sample "512", :value 2.58230398391373}
    {:gene "CACNB2", :sample "509", :value 5.24230161509031}
    {:gene "CACNB2", :sample "517", :value 4.58122522039289}
    {:gene "CACNB2", :sample "516", :value 1.48703785665958}
    {:gene "CACNB2", :sample "513", :value 5.83652615191799}
    {:gene "FAM107A", :sample "508", :value -0.639114866992957}
    {:gene "FAM107A", :sample "520", :value -1.76598130170826}
    {:gene "FAM107A", :sample "521", :value 3.26276217161494}
    {:gene "FAM107A", :sample "512", :value 0.580523267025356}
    {:gene "FAM107A", :sample "509", :value 3.83238908678589}
    {:gene "FAM107A", :sample "517", :value 1.17570994805675}
    {:gene "FAM107A", :sample "516", :value -2.93197221657965}
    {:gene "FAM107A", :sample "513", :value 4.97415813302148}
    {:gene "MAOA", :sample "508", :value 4.34615499416104}
    {:gene "MAOA", :sample "520", :value 3.53657663182848}
    {:gene "MAOA", :sample "521", :value 7.42383345715469}
    {:gene "MAOA", :sample "512", :value 4.53361197669738}
    {:gene "MAOA", :sample "509", :value 7.58181859772461}
    {:gene "MAOA", :sample "517", :value 7.73478409076481}
    {:gene "MAOA", :sample "516", :value 4.70786106177427}
    {:gene "MAOA", :sample "513", :value 7.75164740340675}
    ))

(defn ui
  []
  [:div
   [:p "A recreation of an R example from " [:a {:href "https://bioinformatics.ccr.cancer.gov/docs/btep-coding-club/CC2023/complex_heatmap_enhanced_volcano/"} "here"]]

   [:div.row
    [:div.col-4
     [ch/heatmap data2 :gene :sample :value {:color-scheme "blueorange" :cell-gap 0}]]
    [:div.col-8
     [:img {:src "https://bioinformatics.ccr.cancer.gov/docs/btep-coding-club/CC2023/complex_heatmap_enhanced_volcano_files/figure-html/unnamed-chunk-6-1.png"
            :height 500}]]]])
