var mongoose = require('mongoose');
//MongoDB schema for Animal
module.exports = mongoose.model("Animal", {
    img_addr:{
        type: String
    },
    animal_name:{
        type: String,
        default: "None"
    },
    health_issue:{
        type: String,
        default: "None"
    },
    found_by_user: {
        type: String,   
    },
    owner: {
        type: String,   
        default: "None"
    },
    landmark:{
        type: String,
        default: "None"
    },
    found_by_ngo: {
        type:String,
        default: "None"
    },
    found_by_ngo_email: {
        type:String,
        default: "None"
    },
    found_lat:{
        type: Number,
        
    },
    found_lon:{
        type: Number,
       
    },
    status: {
        type: String,
        default: "Waiting for help"
    }
});