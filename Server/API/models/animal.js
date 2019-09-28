var mongoose = require('mongoose');
//MongoDB schema for Animal
module.exports = mongoose.model("User", {
    health_issue:{
        type: Array,
        required: true
    },
    found_by_user: {
        type: String,
        required: true
    },
    found_by_ngo: {
        type:String,
        default: "None"
    },
    found_lat:{
        type: Float64Array,
        required: true
    },
    found_lon:{
        type: Float64Array,
        required: true
    },
    status: {
        type: String,
        default: "Waiting for help"
    }
});