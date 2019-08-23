var mongoose = require('mongoose');
//MongoDB schema for users
module.exports = mongoose.model("User", {
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
        default: false
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