// This is the Vega spec generated for the basic heatmap example, in standard Vega JSON form
// TODO update, would be nice to automate

{
  "description": "A clustered heatmap with side-dendrograms",
  "$schema": "https://vega.github.io/schema/vega/v5.json",
  "layout": {"align": "each", "columns": 2},
  "data": [
    {
      "name": "hm",
      "values": [
        {"gene": "MT2A", "sample": "512", "value": 5.78285535349592},
        {"gene": "DNAJB4", "sample": "513", "value": 6.87435015921954},
        {"gene": "TIMP4", "sample": "513", "value": 3.37516813739973},
        {"gene": "VCAM1", "sample": "516", "value": 5.76263513289002},
        {"gene": "MT2A", "sample": "509", "value": 8.27698758640279},
        {"gene": "HIF3A", "sample": "517", "value": 2.83358793070328},
        {"gene": "DNM1", "sample": "512", "value": 5.66002401990528},
        {"gene": "ZBTB16", "sample": "520", "value": -0.515040727317009},
        {"gene": "ADAM12", "sample": "508", "value": 6.67785831073959},
        {"gene": "VCAM1", "sample": "508", "value": 5.39422051601125},
        {"gene": "MT2A", "sample": "520", "value": 5.91636282515681},
        {"gene": "MAOA", "sample": "508", "value": 4.34615499416104},
        {"gene": "WNT2", "sample": "517", "value": -0.165525201539909},
        {"gene": "SPARCL1", "sample": "517", "value": 6.00586264525223},
        {"gene": "NEXN", "sample": "508", "value": 6.38633543860892},
        {"gene": "FAM107A", "sample": "520", "value": -1.76598130170826},
        {"gene": "HIF3A", "sample": "521", "value": 4.79058092806672},
        {"gene": "FGD4", "sample": "513", "value": 6.50205756195231},
        {"gene": "DUSP1", "sample": "509", "value": 8.01907429882787},
        {"gene": "CACNB2", "sample": "513", "value": 5.83652615191799},
        {"gene": "SPARCL1", "sample": "516", "value": 2.02834001111556},
        {"gene": "ADAM12", "sample": "516", "value": 6.86031250858483},
        {"gene": "ADAM12", "sample": "521", "value": 4.14377753052124},
        {"gene": "DUSP1", "sample": "516", "value": 5.02834170722824},
        {"gene": "ACSS1", "sample": "512", "value": 3.86937676723286},
        {"gene": "DUSP1", "sample": "521", "value": 8.39670934427623},
        {"gene": "FAM107A", "sample": "512", "value": 0.580523267025356},
        {"gene": "MAOA", "sample": "520", "value": 3.53657663182848},
        {"gene": "FAM107A", "sample": "508", "value": -0.639114866992957},
        {"gene": "TIMP4", "sample": "509", "value": 3.76820277073184},
        {"gene": "STEAP2", "sample": "516", "value": 5.34380759907602},
        {"gene": "DNM1", "sample": "517", "value": 3.9603755075091},
        {"gene": "CACNB2", "sample": "516", "value": 1.48703785665958},
        {"gene": "NEXN", "sample": "513", "value": 8.8347306816487},
        {"gene": "ACSS1", "sample": "516", "value": 2.91630176912867},
        {"gene": "WNT2", "sample": "512", "value": 5.98372036486048},
        {"gene": "NEXN", "sample": "512", "value": 6.79475336433913},
        {"gene": "TIMP4", "sample": "517", "value": 4.25311956115208},
        {"gene": "ZBTB16", "sample": "512", "value": -1.7771519191856},
        {"gene": "NEXN", "sample": "509", "value": 8.60998436036047},
        {"gene": "TIMP4", "sample": "512", "value": 0.580523267025356},
        {"gene": "MT2A", "sample": "521", "value": 7.93349175824132},
        {"gene": "ZBTB16", "sample": "513", "value": 4.9022228878453},
        {"gene": "ACSS1", "sample": "521", "value": 5.99510828523058},
        {"gene": "TIMP4", "sample": "521", "value": 3.63867920029566},
        {"gene": "WNT2", "sample": "521", "value": 1.2370000288886},
        {"gene": "CACNB2", "sample": "508", "value": 1.91233417188757},
        {"gene": "FGD4", "sample": "512", "value": 4.2162201879756},
        {"gene": "FGD4", "sample": "517", "value": 6.30382156424754},
        {"gene": "ZBTB16", "sample": "521", "value": 5.7791730037569},
        {"gene": "DNM1", "sample": "508", "value": 6.18073526217034},
        {"gene": "WNT2", "sample": "520", "value": 4.28285130191303},
        {"gene": "PDPN", "sample": "508", "value": 5.35753249909343},
        {"gene": "HIF3A", "sample": "520", "value": 2.25012442319609},
        {"gene": "PRSS35", "sample": "516", "value": 4.84830664593644},
        {"gene": "WNT2", "sample": "516", "value": 2.11057837942801},
        {"gene": "PRSS35", "sample": "508", "value": 3.93001559453838},
        {"gene": "HIF3A", "sample": "516", "value": 0.492887784795945},
        {"gene": "CACNB2", "sample": "520", "value": 2.31037269750291},
        {"gene": "NEXN", "sample": "517", "value": 8.42466113213688},
        {"gene": "DNM1", "sample": "521", "value": 4.48895537322577},
        {"gene": "DNAJB4", "sample": "520", "value": 5.06014990916259},
        {"gene": "ADAM12", "sample": "509", "value": 4.82250784199589},
        {"gene": "NEXN", "sample": "516", "value": 6.47359951916285},
        {"gene": "FAM107A", "sample": "509", "value": 3.83238908678589},
        {"gene": "FAM107A", "sample": "521", "value": 3.26276217161494},
        {"gene": "PDPN", "sample": "517", "value": 5.02574217372951},
        {"gene": "ZBTB16", "sample": "509", "value": 5.25796696285121},
        {"gene": "VCAM1", "sample": "509", "value": 1.71977948787564},
        {"gene": "SPARCL1", "sample": "521", "value": 6.71983276215129},
        {"gene": "HIF3A", "sample": "509", "value": 3.37445673080234},
        {"gene": "DUSP1", "sample": "520", "value": 5.14324586287326},
        {"gene": "MAOA", "sample": "516", "value": 4.70786106177427},
        {"gene": "ACSS1", "sample": "517", "value": 4.88790377026001},
        {"gene": "ZBTB16", "sample": "508", "value": -1.86352251820081},
        {"gene": "WNT2", "sample": "508", "value": 4.69455368790816},
        {"gene": "NEXN", "sample": "521", "value": 8.71889675737361},
        {"gene": "DUSP1", "sample": "517", "value": 7.72298976466619},
        {"gene": "PRSS35", "sample": "517", "value": 2.06814189565329},
        {"gene": "TIMP4", "sample": "520", "value": 0.819552014211164},
        {"gene": "FGD4", "sample": "508", "value": 4.13241331926396},
        {"gene": "SPARCL1", "sample": "508", "value": 1.55470970310097},
        {"gene": "FGD4", "sample": "509", "value": 6.2395775164949},
        {"gene": "DNAJB4", "sample": "509", "value": 6.5087197277283},
        {"gene": "ADAM12", "sample": "520", "value": 5.81732874866786},
        {"gene": "MT2A", "sample": "508", "value": 6.24851399016412},
        {"gene": "STEAP2", "sample": "508", "value": 5.90927303326086},
        {"gene": "CACNB2", "sample": "517", "value": 4.58122522039289},
        {"gene": "NEXN", "sample": "520", "value": 6.85334055833747},
        {"gene": "PRSS35", "sample": "512", "value": 5.18907269381948},
        {"gene": "STEAP2", "sample": "509", "value": 7.72325041735153},
        {"gene": "STEAP2", "sample": "512", "value": 6.0463676874266},
        {"gene": "DNAJB4", "sample": "517", "value": 6.70020334810133},
        {"gene": "DNM1", "sample": "516", "value": 5.8002922874965},
        {"gene": "PRSS35", "sample": "513", "value": 2.64982582436249},
        {"gene": "STEAP2", "sample": "520", "value": 5.94039233384028},
        {"gene": "FGD4", "sample": "520", "value": 4.58981850877196},
        {"gene": "MT2A", "sample": "517", "value": 8.20158550590639},
        {"gene": "TIMP4", "sample": "508", "value": 0.598793673732585},
        {"gene": "FGD4", "sample": "521", "value": 7.03908171742351},
        {"gene": "CACNB2", "sample": "521", "value": 5.60468736900362},
        {"gene": "TIMP4", "sample": "516", "value": 1.5275170040646},
        {"gene": "SPARCL1", "sample": "509", "value": 6.72069644167212},
        {"gene": "ACSS1", "sample": "508", "value": 3.71483726631926},
        {"gene": "SPARCL1", "sample": "513", "value": 6.34935521091851},
        {"gene": "FAM107A", "sample": "517", "value": 1.17570994805675},
        {"gene": "VCAM1", "sample": "512", "value": 5.58242033138669},
        {"gene": "MAOA", "sample": "509", "value": 7.58181859772461},
        {"gene": "HIF3A", "sample": "513", "value": 4.15474019163534},
        {"gene": "PRSS35", "sample": "521", "value": 1.607251411321},
        {"gene": "PDPN", "sample": "509", "value": 7.20677629425236},
        {"gene": "DNAJB4", "sample": "521", "value": 6.54135805502078},
        {"gene": "CACNB2", "sample": "512", "value": 2.58230398391373},
        {"gene": "DNM1", "sample": "520", "value": 6.28530033555224},
        {"gene": "DNM1", "sample": "509", "value": 4.44196514365529},
        {"gene": "MT2A", "sample": "513", "value": 8.07084582202555},
        {"gene": "HIF3A", "sample": "512", "value": 1.57525031122943},
        {"gene": "VCAM1", "sample": "521", "value": 0.694381107656567},
        {"gene": "STEAP2", "sample": "521", "value": 7.93029052160909},
        {"gene": "WNT2", "sample": "509", "value": 1.3328580234688},
        {"gene": "SPARCL1", "sample": "520", "value": 2.03488498060457},
        {"gene": "ACSS1", "sample": "513", "value": 5.79920433839758},
        {"gene": "PDPN", "sample": "512", "value": 4.35562719096266},
        {"gene": "VCAM1", "sample": "513", "value": 1.96834777623529},
        {"gene": "ADAM12", "sample": "512", "value": 6.98451044997398},
        {"gene": "WNT2", "sample": "513", "value": 2.89864835625364},
        {"gene": "ACSS1", "sample": "509", "value": 5.56550314244328},
        {"gene": "ADAM12", "sample": "513", "value": 4.96455744842029},
        {"gene": "FAM107A", "sample": "513", "value": 4.97415813302148},
        {"gene": "DNAJB4", "sample": "516", "value": 5.2410588772593},
        {"gene": "PDPN", "sample": "521", "value": 5.99287820478704},
        {"gene": "DUSP1", "sample": "513", "value": 8.30219557783798},
        {"gene": "PRSS35", "sample": "509", "value": 1.14344085971871},
        {"gene": "HIF3A", "sample": "508", "value": 1.0139912580994},
        {"gene": "DUSP1", "sample": "508", "value": 4.93655107030217},
        {"gene": "MAOA", "sample": "513", "value": 7.75164740340675},
        {"gene": "PDPN", "sample": "520", "value": 4.18969108667715},
        {"gene": "DNAJB4", "sample": "508", "value": 5.03276842874731},
        {"gene": "ZBTB16", "sample": "517", "value": 4.28308662399116},
        {"gene": "DUSP1", "sample": "512", "value": 5.60756813037785},
        {"gene": "MT2A", "sample": "516", "value": 5.75838967893522},
        {"gene": "DNM1", "sample": "513", "value": 3.98151281042034},
        {"gene": "DNAJB4", "sample": "512", "value": 5.2842361428814},
        {"gene": "MAOA", "sample": "512", "value": 4.53361197669738},
        {"gene": "PRSS35", "sample": "520", "value": 4.48262215247243},
        {"gene": "FGD4", "sample": "516", "value": 4.31271009913433},
        {"gene": "ACSS1", "sample": "520", "value": 4.23313747152727},
        {"gene": "MAOA", "sample": "517", "value": 7.73478409076481},
        {"gene": "STEAP2", "sample": "513", "value": 8.15485426186916},
        {"gene": "ZBTB16", "sample": "516", "value": -2.93197221657965},
        {"gene": "CACNB2", "sample": "509", "value": 5.24230161509031},
        {"gene": "MAOA", "sample": "521", "value": 7.42383345715469},
        {"gene": "ADAM12", "sample": "517", "value": 5.10896456918063},
        {"gene": "PDPN", "sample": "516", "value": 3.12195409198714},
        {"gene": "VCAM1", "sample": "520", "value": 5.03804927591473},
        {"gene": "FAM107A", "sample": "516", "value": -2.93197221657965},
        {"gene": "VCAM1", "sample": "517", "value": 2.53465016621424},
        {"gene": "SPARCL1", "sample": "512", "value": 2.02259555555733},
        {"gene": "PDPN", "sample": "513", "value": 6.28395625499249},
        {"gene": "STEAP2", "sample": "517", "value": 7.2693879518966}
      ]
    },
    {
      "name": "ltree",
      "values": [
        {
          "id": "CACNB2-SPARCL1-HIF3A-TIMP4-FAM107A-ZBTB16-WNT2-PRSS35-VCAM1-ADAM12-DNM1-NEXN-DUSP1-MT2A-STEAP2-ACSS1-PDPN-MAOA-DNAJB4-FGD4",
          "y": 217.578125,
          "x": 0,
          "depth": 0,
          "children": 2
        },
        {
          "id": "WNT2-PRSS35-VCAM1",
          "parent": "WNT2-PRSS35-VCAM1-ADAM12-DNM1-NEXN-DUSP1-MT2A-STEAP2-ACSS1-PDPN-MAOA-DNAJB4-FGD4",
          "y": 28.125,
          "x": 35.714285714285715,
          "depth": 2,
          "children": 2
        },
        {
          "id": "ACSS1-PDPN",
          "parent": "ACSS1-PDPN-MAOA-DNAJB4-FGD4",
          "y": 118.75,
          "x": 42.85714285714286,
          "depth": 5,
          "children": 2
        },
        {
          "id": "PDPN",
          "parent": "ACSS1-PDPN",
          "y": 112.5,
          "x": 50,
          "depth": 6,
          "children": 0
        },
        {
          "id": "MT2A-STEAP2",
          "parent": "DUSP1-MT2A-STEAP2",
          "y": 243.75,
          "x": 42.85714285714286,
          "depth": 6,
          "children": 2
        },
        {
          "id": "VCAM1",
          "parent": "PRSS35-VCAM1",
          "y": 37.5,
          "x": 50,
          "depth": 4,
          "children": 0
        },
        {
          "id": "WNT2",
          "parent": "WNT2-PRSS35-VCAM1",
          "y": 12.5,
          "x": 50,
          "depth": 3,
          "children": 0
        },
        {
          "id": "STEAP2",
          "parent": "MT2A-STEAP2",
          "y": 237.5,
          "x": 50,
          "depth": 7,
          "children": 0
        },
        {
          "id": "CACNB2",
          "parent": "CACNB2-SPARCL1",
          "y": 300,
          "x": 50,
          "depth": 4,
          "children": 0
        },
        {
          "id": "DNAJB4",
          "parent": "DNAJB4-FGD4",
          "y": 150,
          "x": 50,
          "depth": 7,
          "children": 0
        },
        {
          "id": "DUSP1",
          "parent": "DUSP1-MT2A-STEAP2",
          "y": 275,
          "x": 50,
          "depth": 6,
          "children": 0
        },
        {
          "id": "HIF3A",
          "parent": "HIF3A-TIMP4",
          "y": 337.5,
          "x": 50,
          "depth": 4,
          "children": 0
        },
        {
          "id": "ADAM12-DNM1-NEXN-DUSP1-MT2A-STEAP2-ACSS1-PDPN-MAOA-DNAJB4-FGD4",
          "parent": "WNT2-PRSS35-VCAM1-ADAM12-DNM1-NEXN-DUSP1-MT2A-STEAP2-ACSS1-PDPN-MAOA-DNAJB4-FGD4",
          "y": 135.9375,
          "x": 14.285714285714285,
          "depth": 2,
          "children": 2
        },
        {
          "id": "ACSS1",
          "parent": "ACSS1-PDPN",
          "y": 125,
          "x": 50,
          "depth": 6,
          "children": 0
        },
        {
          "id": "ADAM12-DNM1",
          "parent": "ADAM12-DNM1-NEXN-DUSP1-MT2A-STEAP2-ACSS1-PDPN-MAOA-DNAJB4-FGD4",
          "y": 81.25,
          "x": 42.85714285714286,
          "depth": 3,
          "children": 2
        },
        {
          "id": "FGD4",
          "parent": "DNAJB4-FGD4",
          "y": 162.5,
          "x": 50,
          "depth": 7,
          "children": 0
        },
        {
          "id": "SPARCL1",
          "parent": "CACNB2-SPARCL1",
          "y": 312.5,
          "x": 50,
          "depth": 4,
          "children": 0
        },
        {
          "id": "ACSS1-PDPN-MAOA-DNAJB4-FGD4",
          "parent": "NEXN-DUSP1-MT2A-STEAP2-ACSS1-PDPN-MAOA-DNAJB4-FGD4",
          "y": 145.3125,
          "x": 28.57142857142857,
          "depth": 4,
          "children": 2
        },
        {
          "id": "MT2A",
          "parent": "MT2A-STEAP2",
          "y": 250,
          "x": 50,
          "depth": 7,
          "children": 0
        },
        {
          "id": "DNM1",
          "parent": "ADAM12-DNM1",
          "y": 75,
          "x": 50,
          "depth": 4,
          "children": 0
        },
        {
          "id": "PRSS35",
          "parent": "PRSS35-VCAM1",
          "y": 50,
          "x": 50,
          "depth": 4,
          "children": 0
        },
        {
          "id": "DNAJB4-FGD4",
          "parent": "MAOA-DNAJB4-FGD4",
          "y": 156.25,
          "x": 42.85714285714286,
          "depth": 6,
          "children": 2
        },
        {
          "id": "WNT2-PRSS35-VCAM1-ADAM12-DNM1-NEXN-DUSP1-MT2A-STEAP2-ACSS1-PDPN-MAOA-DNAJB4-FGD4",
          "parent": "CACNB2-SPARCL1-HIF3A-TIMP4-FAM107A-ZBTB16-WNT2-PRSS35-VCAM1-ADAM12-DNM1-NEXN-DUSP1-MT2A-STEAP2-ACSS1-PDPN-MAOA-DNAJB4-FGD4",
          "y": 82.03125,
          "x": 7.142857142857145,
          "depth": 1,
          "children": 2
        },
        {
          "id": "ZBTB16",
          "parent": "FAM107A-ZBTB16",
          "y": 375,
          "x": 50,
          "depth": 3,
          "children": 0
        },
        {
          "id": "FAM107A",
          "parent": "FAM107A-ZBTB16",
          "y": 387.5,
          "x": 50,
          "depth": 3,
          "children": 0
        },
        {
          "id": "NEXN",
          "parent": "NEXN-DUSP1-MT2A-STEAP2",
          "y": 212.5,
          "x": 50,
          "depth": 5,
          "children": 0
        },
        {
          "id": "CACNB2-SPARCL1",
          "parent": "CACNB2-SPARCL1-HIF3A-TIMP4",
          "y": 306.25,
          "x": 42.85714285714286,
          "depth": 3,
          "children": 2
        },
        {
          "id": "DUSP1-MT2A-STEAP2",
          "parent": "NEXN-DUSP1-MT2A-STEAP2",
          "y": 259.375,
          "x": 35.714285714285715,
          "depth": 5,
          "children": 2
        },
        {
          "id": "TIMP4",
          "parent": "HIF3A-TIMP4",
          "y": 350,
          "x": 50,
          "depth": 4,
          "children": 0
        },
        {
          "id": "CACNB2-SPARCL1-HIF3A-TIMP4",
          "parent": "CACNB2-SPARCL1-HIF3A-TIMP4-FAM107A-ZBTB16",
          "y": 325,
          "x": 35.714285714285715,
          "depth": 2,
          "children": 2
        },
        {
          "id": "CACNB2-SPARCL1-HIF3A-TIMP4-FAM107A-ZBTB16",
          "parent": "CACNB2-SPARCL1-HIF3A-TIMP4-FAM107A-ZBTB16-WNT2-PRSS35-VCAM1-ADAM12-DNM1-NEXN-DUSP1-MT2A-STEAP2-ACSS1-PDPN-MAOA-DNAJB4-FGD4",
          "y": 353.125,
          "x": 28.57142857142857,
          "depth": 1,
          "children": 2
        },
        {
          "id": "HIF3A-TIMP4",
          "parent": "CACNB2-SPARCL1-HIF3A-TIMP4",
          "y": 343.75,
          "x": 42.85714285714286,
          "depth": 3,
          "children": 2
        },
        {
          "id": "MAOA",
          "parent": "MAOA-DNAJB4-FGD4",
          "y": 187.5,
          "x": 50,
          "depth": 6,
          "children": 0
        },
        {
          "id": "NEXN-DUSP1-MT2A-STEAP2-ACSS1-PDPN-MAOA-DNAJB4-FGD4",
          "parent": "ADAM12-DNM1-NEXN-DUSP1-MT2A-STEAP2-ACSS1-PDPN-MAOA-DNAJB4-FGD4",
          "y": 190.625,
          "x": 21.42857142857143,
          "depth": 3,
          "children": 2
        },
        {
          "id": "ADAM12",
          "parent": "ADAM12-DNM1",
          "y": 87.5,
          "x": 50,
          "depth": 4,
          "children": 0
        },
        {
          "id": "MAOA-DNAJB4-FGD4",
          "parent": "ACSS1-PDPN-MAOA-DNAJB4-FGD4",
          "y": 171.875,
          "x": 35.714285714285715,
          "depth": 5,
          "children": 2
        },
        {
          "id": "PRSS35-VCAM1",
          "parent": "WNT2-PRSS35-VCAM1",
          "y": 43.75,
          "x": 42.85714285714286,
          "depth": 3,
          "children": 2
        },
        {
          "id": "FAM107A-ZBTB16",
          "parent": "CACNB2-SPARCL1-HIF3A-TIMP4-FAM107A-ZBTB16",
          "y": 381.25,
          "x": 42.85714285714286,
          "depth": 2,
          "children": 2
        },
        {
          "id": "NEXN-DUSP1-MT2A-STEAP2",
          "parent": "NEXN-DUSP1-MT2A-STEAP2-ACSS1-PDPN-MAOA-DNAJB4-FGD4",
          "y": 235.9375,
          "x": 28.57142857142857,
          "depth": 4,
          "children": 2
        }
      ],
      "transform": [
        {"type": "stratify", "key": "id", "parentKey": "parent"},
        {
          "type": "tree",
          "method": "cluster",
          "size": [{"signal": "hm_height"}, {"signal": "dend_width"}],
          "as": ["y", "x", "depth", "children"]
        }
      ]
    },
    {
      "name": "ltree-leaf",
      "source": "ltree",
      "transform": [{"type": "filter", "expr": "datum.children == 0"}]
    },
    {
      "name": "utree",
      "values": [
        {
          "id": "516-520-508-512-517-513-509-521",
          "x": 88.57142857142858,
          "y": 0,
          "depth": 0,
          "children": 2
        },
        {
          "id": "508",
          "parent": "508-512",
          "x": 114.28571428571429,
          "y": 50,
          "depth": 4,
          "children": 0
        },
        {
          "id": "520",
          "parent": "520-508-512",
          "x": 91.42857142857142,
          "y": 50,
          "depth": 3,
          "children": 0
        },
        {
          "id": "509-521",
          "parent": "513-509-521",
          "x": 17.142857142857142,
          "y": 37.5,
          "depth": 3,
          "children": 2
        },
        {
          "id": "513-509-521",
          "parent": "517-513-509-521",
          "x": 31.428571428571427,
          "y": 25,
          "depth": 2,
          "children": 2
        },
        {
          "id": "521",
          "parent": "509-521",
          "x": 11.428571428571427,
          "y": 50,
          "depth": 4,
          "children": 0
        },
        {
          "id": "512",
          "parent": "508-512",
          "x": 125.71428571428571,
          "y": 50,
          "depth": 4,
          "children": 0
        },
        {
          "id": "508-512",
          "parent": "520-508-512",
          "x": 120,
          "y": 37.5,
          "depth": 3,
          "children": 2
        },
        {
          "id": "517-513-509-521",
          "parent": "516-520-508-512-517-513-509-521",
          "x": 50,
          "y": 12.5,
          "depth": 1,
          "children": 2
        },
        {
          "id": "520-508-512",
          "parent": "516-520-508-512",
          "x": 105.71428571428571,
          "y": 25,
          "depth": 2,
          "children": 2
        },
        {
          "id": "509",
          "parent": "509-521",
          "x": 22.857142857142854,
          "y": 50,
          "depth": 4,
          "children": 0
        },
        {
          "id": "517",
          "parent": "517-513-509-521",
          "x": 68.57142857142857,
          "y": 50,
          "depth": 2,
          "children": 0
        },
        {
          "id": "516",
          "parent": "516-520-508-512",
          "x": 148.57142857142858,
          "y": 50,
          "depth": 2,
          "children": 0
        },
        {
          "id": "513",
          "parent": "513-509-521",
          "x": 45.71428571428571,
          "y": 50,
          "depth": 3,
          "children": 0
        },
        {
          "id": "516-520-508-512",
          "parent": "516-520-508-512-517-513-509-521",
          "x": 127.14285714285714,
          "y": 12.5,
          "depth": 1,
          "children": 2
        }
      ],
      "transform": [
        {"type": "stratify", "key": "id", "parentKey": "parent"},
        {
          "type": "tree",
          "method": "cluster",
          "size": [{"signal": "hm_width"}, {"signal": "dend_width"}],
          "as": ["x", "y", "depth", "children"]
        }
      ]
    },
    {
      "name": "utree-leaf",
      "source": "utree",
      "transform": [{"type": "filter", "expr": "datum.children == 0"}]
    }
  ],
  "scales": [
    {
      "name": "sx",
      "type": "band",
      "domain": {
        "data": "utree-leaf",
        "field": "id",
        "sort": {"field": "x", "op": "min"}
      },
      "range": {"step": 20}
    },
    {
      "name": "sy",
      "type": "band",
      "domain": {
        "data": "ltree-leaf",
        "field": "id",
        "sort": {"field": "y", "op": "min"}
      },
      "range": {"step": 20}
    },
    {
      "name": "color",
      "type": "linear",
      "range": {"scheme": "BlueOrange"},
      "domain": {"data": "hm", "field": "value"}
    }
  ],
  "signals": [
    {"name": "hm_width", "value": 160},
    {"name": "hm_height", "value": 400},
    {"name": "dend_width", "value": 50}
  ],
  "padding": 5,
  "marks": [
    {
      "type": "group",
      "style": "cell",
      "encode": {
        "enter": {
          "width": {"signal": "dend_width"},
          "height": {"signal": "dend_width"},
          "strokeWidth": {"value": 0}
        }
      }
    },
    {
      "type": "group",
      "style": "cell",
      "data": [
        {
          "name": "links",
          "source": "utree",
          "transform": [
            {"type": "treelinks"},
            {"type": "linkpath", "orient": "vertical", "shape": "orthogonal"}
          ]
        }
      ],
      "encoding": {
        "width": {"signal": "hm_width"},
        "height": {"signal": "dend_width"},
        "strokeWidth": {"value": 0}
      },
      "marks": [
        {
          "type": "path",
          "from": {"data": "links"},
          "encode": {
            "enter": {"path": {"field": "path"}, "stroke": {"value": "#666"}}
          }
        }
      ]
    },
    {
      "type": "group",
      "style": "cell",
      "data": [
        {
          "name": "links",
          "source": "ltree",
          "transform": [
            {"type": "treelinks"},
            {"type": "linkpath", "orient": "horizontal", "shape": "orthogonal"}
          ]
        }
      ],
      "encoding": {
        "width": {"signal": "dend_width"},
        "height": {"signal": "hm_height"},
        "strokeWidth": {"value": 0}
      },
      "marks": [
        {
          "type": "path",
          "from": {"data": "links"},
          "encode": {
            "enter": {"path": {"field": "path"}, "stroke": {"value": "#666"}}
          }
        }
      ]
    },
    {
      "type": "group",
      "name": "heatmap",
      "style": "cell",
      "encode": {
        "update": {
          "width": {"signal": "hm_width"},
          "height": {"signal": "hm_height"}
        }
      },
      "axes": [
        {"orient": "right", "scale": "sy", "domain": false, "title": "gene"},
        {
          "orient": "bottom",
          "scale": "sx",
          "labelAngle": 90,
          "labelAlign": "left",
          "labelBaseline": "middle",
          "domain": false,
          "title": "sample"
        }
      ],
      "legends": [
        {
          "fill": "color",
          "type": "gradient",
          "title": "value",
          "titleOrient": "bottom",
          "gradientLength": {"signal": "hm_height / 2"}
        }
      ],
      "marks": [
        {
          "type": "rect",
          "from": {"data": "hm"},
          "encode": {
            "enter": {
              "y": {"field": "gene", "scale": "sy"},
              "x": {"field": "sample", "scale": "sx"},
              "width": {"value": 19},
              "height": {"value": 19},
              "fill": {"field": "value", "scale": "color"}
            }
          }
        }
      ]
    }
  ]
}
