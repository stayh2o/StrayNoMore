var express = require('express');
var app = express();
var path = require('path');
var http = require("http").Server(app);
var bodyParser = require('body-parser')
var multer = require('multer');
var mongoose = require('mongoose');
var User = require('./models/user');
var Ngo = require('./models/ngo');
var Animal = require("./models/animal");
var Transaction = require("./models/transaction");
var fs = require('fs');
var ngodata = require('./ngo.json')
const swig = require('swig')
let ip = 'http://192.168.43.77:8081/'
app.use(express.static('./uploads'))

app.engine('html', swig.renderFile)
app.set('views', path.join(__dirname, 'public'))
app.set('view engine', 'html')
app.set('view cache', false)
//swig.setDefaults({ cache: false, varControls: ['<%=', '%>'] })

app.use(express.static(path.join(__dirname, 'public')));

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

app.use(bodyParser.json({limit: "50mb"}));
app.use(bodyParser.urlencoded({ extended: false }));

app.get('/', (req, res) => {
    //console.log(limit)
    res.render('main.html')
});

app.get('/weblogin', (req, res) => {
    res.render('login.html')
});
app.post('/weblogin',(req,res)=>{
    //console.log(req.body)
    Ngo.findOne({email:req.body.email, password:req.body.password},(error,data)=>{
        if(error){
            return res.sendStatus(404);
        }
        else{
            console.log(data)
            return res.render('request.html',{data})
        }
    })
})

app.get('/webregister', (req, res) => {
    res.render('Register.html')
});
app.post('/webregister', (req, res) => {
   console.log(req.body)
   var data = new Ngo(req.body);
   data.save((error,tx)=>{
       if(error){
           console.log(error)
       }
       else{
            return res.redirect('/weblogin')
       } 
   })
});

app.get('/putadopt/:id',(req,res)=>{
    //let data = req.params.id;
    //res.render('adopt.html',{data})
    Ngo.findOne({email:req.params.id},(error,data)=>{
        if(error){
            console.log(error)
        }
        else{
            console.log(data)
            res.render('adopt.html',{data})
        }
    })
})
app.post('/putadopt',upload.single('image'),(req,res)=>{
    let ani = new Animal(req.body)
    //console.log(ani)
    ani.img_addr = req.file.filename
    console.log(ani)
    ani.save((error,tx)=>{
        if(error){
            res.sendStatus(404);
        }
        else{
            res.redirect('back');
        }
    })
})

app.get('/adopt',(req,res)=>{
    let table = `<table class="table">
    <thead>
      <tr>
        <th class="text-white mb-4 " scope="col">Image</th>
        <th class="text-white mb-4 " scope="col">Found By</th>
        <th class="text-white mb-4 " scope="col">Landmark</th>
        <th class="text-white mb-4 " scope="col"></th>
      </tr>
    </thead>
    <tbody>`
    Animal.find({},(error,data)=>{
        if(error){
            console.log(error);
            res.sendStatus(400);
        }
        else{
            for (let i=0; i<data.length; i++){
                if(data[i].status == "Waiting for help"){
                    table += `<tr>
                    <td><img src="${ip}${data[i].img_addr}" alt="animal" height="100" width="100"></img></td>
                    <td>${data[i].found_by_user}</td>
                    <td >${data[i].landmark}</td>
                    <td>
                        <button type="button" onclick="helper('${data[i].img_name}')">Rescue</button>
                    <td>
                    </tr>`
                }
            }
            table += `</tbody>
            </table>`
            //console.log(table)
            res.send(table)
        }

    })
})
app.post('/adopt',(req,res)=>{
    console.log(req.body)
    Animal.findOneAndUpdate({img_name:req.body.img_name},{status:"Rescued",found_by_ngo:req.body.found_by_ngo, found_by_ngo_email:req.body.found_by_ngo_email},(error,doc)=>{
        if(error){
            return res.sendStatus(404);
        }
        else{
            res.redirect(req.get('referer'));
        }
    })
})

app.post('/login',(req,res)=>{
    User.findOne({email_id:req.body.email_id, password:req.body.password},(error,data)=>{
        if(error){
            return res.sendStatus(404);
        }
        else{
            console.log("Login");
            res.send(data);
        }
    })
});

app.post('/register',(req,res)=>{
    console.log(req.body.name)
    let name = req.body.email_id+"_profilepic.jpg"
    var per = new User({
        img_addr : name,
        name : req.body.name,
        email_id : req.body.email_id,
        password : req.body.password,
        phone : req.body.phone
    });
    console.log("Register");

    fs.writeFile("./uploads/"+name, req.body.image, {encoding: 'base64'}, function(err){
        if(err){
            console.log(err);
        }
        else{
            console.log("saved");
        }
      });

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
    //console.log(limit)
    let name = req.body.found_by_user+Date.now()+".jpg"
    var animal = new Animal({
        img_addr: name,
        //img_destination: req.body.image,
        landmark: req.body.landmark,
        found_by_user: req.body.found_by_user,
        found_lat: req.body.found_lat,
        found_lon: req.body.found_lon
    })
    fs.writeFile("./uploads/"+name, req.body.image, {encoding: 'base64'}, function(err){
        if(err){
            console.log(err);
        }
        else{
            console.log("saved");
        }
      });
    console.log(animal)
    animal.save(function(err){
        if(err){
            console.log(err);
            res.sendStatus(400);
        }
        else{
            User.findOne({email_id:req.body.found_by_user},(error,data)=>{
                if(error){
                    res.sendStatus(400);
                }
                else{
                    let sr = data.score;
                    User.findOneAndUpdate({email_id:req.body.found_by_user},{score:sr+100},(error,tx)=>{
                        if(error){
                            res.sendStatus(400);
                        }
                        else{
                            res.sendStatus(200);
                        }
                    })
                }
            })
            
        }
    })
})

app.get('/adoptlist',(req,res)=>{
    Animal.find({status:"Up for Adoption"},(error,data)=>{
        if(error){
            console.log(error);
            res.sendStatus(400);
        }
        else{
            res.send(data);
        }

    })
})

app.get('/ngolist',(req,res)=>{

    Ngo.find({},(error,data)=>{
        if(error){
            console.log(error);
            res.sendStatus(400);
        }
        else{
            res.send(data);
        }

    })
    //res.send(ngodata);
})

app.post('/meadopt',(req,res)=>{
    console.log(req.body);
    Animal.findOneAndUpdate({animal_name:req.body.animal_name, found_by_ngo:req.body.found_by_ngo},{status:"Adopted",owner:req.body.owner},(error,tx)=>{
        if(error){
            res.sendStatus(400);
        }
        else{
            User.findOne({email_id:req.body.owner},(error,data)=>{
                if(error){
                    res.sendStatus(400);
                }
                else{
                    let sr = data.score;
                    User.findOneAndUpdate({email_id:req.body.owner},{score:sr+1000},(error,tx)=>{
                        if(error){
                            res.sendStatus(400);
                        }
                        else{
                            res.sendStatus(200);
                        }
                    })
                }
            })
            //res.sendStatus(200);
        }
    })
})

app.get('/mehelp/:id',(req,res)=>{
    Animal.find({found_by_user:req.params.id},(error,data)=>{
        if(error){
            res.sendStatus(200);
        }
        else{
            res.send(data);
        }
    })
})

app.post('/donate',(req,res)=>{
    console.log(req.body);
    let tx = new Transaction(req.body);
    tx.save(function(err){
        if(err){
            console.log(err);
            res.sendStatus(400);
        }
        else{
            User.findOne({email_id:tx.sender_email},(error,data)=>{
                if(error){
                    res.sendStatus(400);
                }
                else{
                    let am = data.amount_don;
                    let sr = data.score;
                    User.findOneAndUpdate({email_id:tx.sender_email},{amount_don:am+parseInt(tx.amount),score:sr+parseInt(tx.amount)},(error,tx)=>{
                        if(error){
                            res.sendStatus(400);
                        }
                        else{
                            res.sendStatus(200);
                        }
                    })
                }
            })
        }
    })
})
app.get('/donate/:id',(req,res)=>{
    let table = `<table class="table">
    <thead>
      <tr>
        <th class="text-white mb-4 " scope="col">Donater</th>
        <th class="text-white mb-4 " scope="col">Amount </th>
        <th class="text-white mb-4 " scope="col">Transaction Id</th>
      </tr>
    </thead>
    <tbody>`
    Transaction.find({ngo_email:req.params.id},(error,data)=>{
        if(error){
            console.log(error);
        }
        else{
            console.log(data.length)
            for (let i=0; i<data.length; i++){
                    table += `<tr>
                    <td>${data[i].sender_email}</td>
                    <td>${data[i].amount}</td>
                    <td >${data[i].txId}</td>
                    </tr>`
            }
            table += `</tbody>
            </table>`
            console.log(table)
            res.send(table)
        }
    })
})

app.get('/user/:id',(req,res)=>{
    User.findOne({email_id:req.params.id},(error,data)=>{
        if(error){
            console.log(error);
        }
        else{
            res.send(data);
        }
    })
})
//creating a server
var server = http.listen(8081, () => {
    console.log("Well done, now I am listening on ", server.address().port)
});
