var mongoose = require('mongoose');
//MongoDB schema for users
module.exports = mongoose.model("User", {
    img_addr:{
        type: String
    },
    name: {
        type: String,
        required:true
    },
    email_id: {
        type: String,
        required: true
    },
    password: {
        type: String,
        required: true
    },
    phone: {
        type: String,
        required: true
    },
    verified: {
        type: Boolean,
        default: true
    },
    amount_don: {
        type: Number,
        default: 0
    },
    score: {
        type: Number,
        default: 0
    }
});