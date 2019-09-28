var express = require('express');
var app = express();
var http = require("http").Server(app);
var bodyParser = require('body-parser')
var mongoose = require('mongoose');
var User = require('./models/user');
var Ngo = require('./models/ngo');
var Animal = require("./models/animal");


mongoose.connect('mongodb://localhost/straynomore');
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'MongoDB connection error:'));

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

app.get('/', (req, res) => {
    res.sendStatus(200)
});

app.post('/login',(req,res)=>{
    console.log("Login");
    User.findOne({email_id:req.body.email_id, password:req.body.password},(error,data)=>{
        if(error){
            return res.sendStatus(404);
        }
        else{
            res.send(data);
        }
    })
});

app.post('/register',(req,res)=>{
    console.log(req.body.name)
    var user = new User(req.body);
    console.log(user);
    user.save((error,tx)=>{
        if(error){
            res.sendStatus(404);
        }
        else{
            res.send(user);
        }
    })
})
//creating a server
var server = http.listen(8081, () => {
    console.log("Well done, now I am listening on ", server.address().port)
});