var express = require('express');
var app = express();
var http = require("http").Server(app);

var User = require('./models/user');


app.get('/', (req, res) => {
    res.sendStatus(200)
});

//creating a server
var server = http.listen(8081, () => {
    console.log("Well done, now I am listening on ", server.address().port)
});