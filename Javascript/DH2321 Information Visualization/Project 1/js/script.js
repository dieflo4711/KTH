var blue_to_brown = d3.scale.linear()
  .domain([9, 50])
  .range(["steelblue", "brown"])
  .interpolate(d3.interpolateLab);

var color = function(d) { return blue_to_brown(d['economy (mpg)']); };

var parcoords = d3.parcoords()("#example")
  .alpha(0.4);

// load csv file and create the chart
d3.csv('data/table.csv', function(data) {
  parcoords
    .data(data)
    .hideAxis(["Alias", "Major"])
    .render()
    .brushMode("1D-axes");  // enable brushing

  // create data table, row hover highlighting
  var grid = d3.divgrid();
  grid.columns(["Alias", "Major"]);
  d3.select("#grid")
    .datum(data.slice(0,65))
    .call(grid)
    .selectAll(".row")
    .on({
      "mouseover": function(d) { parcoords.highlight([d]) },
      "mouseout": parcoords.unhighlight
    });


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
});