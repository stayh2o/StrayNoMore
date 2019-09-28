var mongoose = require('mongoose');
//MongoDB schema for Ngo
module.exports = mongoose.model("Ngo", {
    ngo_name: {
        type: String,
        required: true
    },
    address: {
        type: String,
        required: true
    },
    loc_coor_lat: {
        type: Number,
        required: true
    },
    loc_coor_lon: {
        type: Number,
        required: true
    },
    contact: {
        type: String,
        required: true
    },
    capacity: {
        type: Number,
        default: 0
    },
    cur_occ: {
        type: Number,
        default: 0
    },
    no_occ: {
        type:Number,
        default:0
    },
    verified: {
        type: Boolean,
        default: false
    },
    password: {
        type: String,
        required: true
    }
});