
function showViz(range) {
  var dataset = "";
  var toggle = false;
  var toggleContinent = false;
  var continents_list = [];

  switch(range) {
    case "95_99":
      dataset = "table1.csv";
      break;
    case "00_04":
      dataset = "table2.csv";
      break;
    case "05_09":
      dataset = "table3.csv";
      break;
    case "10_14":
      dataset = "table4.csv";  
      break;
  }

  var discovery = document.getElementsByClassName("discovery-label");

  for(var k = 0; k < discovery.length; k++){
    discovery[k].onclick = function(){toggleDiscovery(this.id)};
  }

  var activeEle = document.getElementsByClassName('activeRange');
  [].forEach.call(activeEle, function(el) {
    el.classList.remove("activeRange");
  });

  var continent_ele = document.getElementById("gridScroll").getElementsByClassName("cell");
  [].forEach.call(continent_ele, function(el) {
    el.classList.remove("active");
  });

  document.getElementById(range).classList.add('activeRange');
  document.getElementById("example").innerHTML = "";
  document.getElementById("grid").innerHTML = "";

  var blue_to_brown = d3.scale.linear()
  .domain([9, 50])
  .range(["steelblue", "brown"])
  .interpolate(d3.interpolateLab);

  var color = function(d) { return blue_to_brown(d['economy (mpg)']); };

  var parcoords = d3.parcoords()("#example")
    .alpha(0.4);
  //
  // load csv file and create the chart
  d3.csv('data/'+dataset, function(data) {
    var tmpData = [];
    
    parcoords
      .data(data)
      .hideAxis(["Countries", "Continents"])
      .render()
      .brushMode("1D-axes");

    var grid = d3.divgrid();
    initGrid("#grid", data);
    
    // update data table on brush event
    parcoords.on("brush", function(d) {
      d3.select("#grid")
        .datum(d.slice(0,65))
        .call(grid)
        .selectAll(".row")
        .on({
          "mouseover": function(d) { parcoords.highlight([d]) },
          "mouseout": parcoords.unhighlight
        });
    });
    
    var gridScroll = d3.divgrid();
    
    initContinentGrid("#gridScroll", getContinentObj(data));
    
    for(var i = 0; i < continent_ele.length; i++) {
      continent_ele[i].onclick = function(){toggleActive(this, 'active')};
    }

    function initGrid(id, d) {
      // create data table, row hover highlighting
      grid.columns(["Countries"]);
      d3.select(id)
        .datum(d.slice(0,65))
        .call(grid)
        .selectAll(".row")
        .on({
          "mouseover": function(d) { parcoords.highlight([d]) },
          "mouseout": parcoords.unhighlight
      });
    }

    function initContinentGrid(id, d) {
      d3.select(id)
        .datum(d.slice(0,65))
        .call(gridScroll)
        .selectAll(".row")
        .on({
          "click": function(d) {
            var filteredData = filterData(d);
            parcoords
            .data(filteredData)
            .hideAxis(["Countries", "Continents"])
            .render()
            .brushMode("1D-axes");

            initGrid("#grid", filteredData);
          }
        });
    }

    function filterData(d){
      var continent = d["Continents"];
      if(continents_list[continent] == 1) {
        remove(continent);
        if(tmpData.length == 0) {
          tmpData = [];
          return data
        }
      } else {
        add(continent);
      }

      return tmpData
    }

    function remove(ele) {
      var tmp = [];
      for(var i = 0; i < Object.keys(tmpData).length; i++) {
        if(tmpData[i]["Continents"] != ele) {
          tmp.push(tmpData[i]);
        }
      }
      continents_list[ele] = 0;
      tmpData = tmp;
    }

    function add(ele) {
      for(var i = 0; i < Object.keys(data).length; i++) {
        if(data[i]["Continents"] == ele) {
          tmpData.push(data[i]);
        }
      }
      continents_list[ele] = 1;
    }

    function getContinentObj(obj) {
      var continents = ['Europe', 'Asia', 'North America', 'South America', 'Oceania', 'Africa'];
      var array = [];
      continents_list = [];
      
      for(var i = 0; i < Object.keys(obj).length; i++) {
        if(continents.includes((obj[i]["Continents"]))) {
          
          var cont = continents.splice(continents.indexOf(obj[i]["Continents"]), 1)[0];
          array.push({Continents: cont});
          continents_list[cont] = 0;
        }
      }
      return array;
    } 
  });
}

var blurContianer = document.getElementById("grid");
var testTable = document.getElementById("gridScroll");

if(blurContianer.clientHeight < testTable.clientHeight) {
    this.addBlur(blurContianer);
}

blurContianer.onscroll = function(){toggleBlur(this)};

function toggleBlur(box) {
  if(box.scrollTop >= box.scrollHeight - box.clientHeight) {
      box.setAttribute('style', '-webkit-mask-image: none');
  } else {
      addBlur(box);
  }
}
function toggleActive(ele, name) {
  if(ele.className == "") {
    ele.className += name;
  } else {
    if(ele.className.indexOf(' ' + name) > -1) {
      ele.className = ele.className.replace(' '+name, '');
    } else if(ele.className.indexOf(name) > -1) {
      ele.className = '';
    } else {
      ele.className += ' ' + name;
    }
  }
}


function addBlur(box) {
  box.setAttribute('style', '-webkit-mask-image: -webkit-gradient(linear,left 80%,left bottom,from(black),to(rgba(0,0,0,0)))');
}
function toggleDiscovery(id) {
  var discovery = document.getElementsByClassName("discovery-label");
  var discoveryContainer = document.getElementsByClassName("discovery-container");

  for(var i = 0; i < discoveryContainer.length; i++) {
    if(discovery[i].id != id) {
      removeClass(discovery[i], 'activeDiscoveryLabel');
    } else {
      addClass(discovery[i], 'activeDiscoveryLabel');
    }
    if(discoveryContainer[i].id != id) {
      discoveryContainer[i].style.display = "none";
    } else {
      discoveryContainer[i].style.display = "block";
    }
  }
}

function addClass(ele, name) {
  if(ele.className == "") {
    ele.className += name;
  } else {
    ele.className += ' ' + name;
  }
}

function removeClass(ele, name) {
  if(ele.className.indexOf(' ' + name) > -1) {
    ele.className = ele.className.replace(' '+name, '');
  } else if(ele.className.indexOf(name) > -1) {
    ele.className = '';
  }
}
