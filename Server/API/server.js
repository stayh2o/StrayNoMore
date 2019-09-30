var express = require('express');
var app = express();
var http = require("http").Server(app);
var bodyParser = require('body-parser')
var multer = require('multer');
var mongoose = require('mongoose');
var User = require('./models/user');
var Ngo = require('./models/ngo');
var Animal = require("./models/animal");

var storage = multer.diskStorage({
    destination: function(req,file,cb){
        cb(null,'uploads');
    },
    filename: function(req,file,cb){
        var og_name = file.originalname;
        var extension = og_name.split('.');
        cb(null, file.fieldname+'-'+Date.now()+'.'+extension[extension.length-1]);
    }
})

var upload = multer({storage: storage});

mongoose.connect('mongodb://localhost/straynomore');
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'MongoDB connection error:'));

// app.use(bodyParser.json());
app.use(bodyParser.json({limit: "50mb", type:'application/json'}));
app.use(bodyParser.urlencoded({limit: "50mb", extended: true, parameterLimit:50000, type:'application/x-www-form-urlencoding'}));

app.get('/', (req, res) => {
    //console.log(limit)
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
    var per = new User(req.body);
    console.log("Register");
    per.save((error,tx)=>{
        if(error){
            res.sendStatus(404);
        }
        else{
            res.send(per);
        }
    })
})

app.post('/upload',(req,res)=>{
    console.log(limit)
    var animal = new Animal({
        img_name: Date.now()+".jpg",
        img_destination: req.body.image,
        found_by_user: req.body.found_by_user,
        found_lat: req.body.found_lat,
        found_lon: req.body.found_lon
    })
    console.log(animal)
    animal.save(function(err){
        if(err){
            console.log(err);
            res.sendStatus(400);
        }
        else{
            res.sendStatus(200);
        }
    })
})

//creating a server
var server = http.listen(8081, () => {
    console.log("Well done, now I am listening on ", server.address().port)
});
