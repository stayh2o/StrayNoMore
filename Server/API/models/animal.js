var mongoose = require('mongoose');
//MongoDB schema for Animal
module.exports = mongoose.model("Animal", {
    img_name:{
        type: String
    },
    img_destination:{
        type: String
    },
    health_issue:{
        type: Array,
       
    },
    found_by_user: {
        type: String,   
    },
    landmark:{
        type: String,
    },
    found_by_ngo: {
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