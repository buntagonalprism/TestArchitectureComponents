var express = require('express'),
app = express(),
port = process.env.PORT || 3000;
const uuid = require('uuid/v4');

app.listen(port);

var SIMULATE_LAG_MS = 10000;

app.route("/books")
    .get(function(req, resp) {
        setTimeout(function() {
            resp.send([{"title":"Winds of Winter", "id": uuid(), "description" : "Things start to get a little frosty", "author" : "George RR Martin" }]);
        }, SIMULATE_LAG_MS);
    })

console.log('todo list RESTful API server started on: ' + port);