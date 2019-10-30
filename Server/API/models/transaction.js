var mongoose = require('mongoose');
//MongoDB schema for users
module.exports = mongoose.model("Transaction", {
    sender_email:{
        type: String
    },
    txId: {
        type: String,
        required:true
    },
    ngo_email: {
        type: String,
        required: true
    },
    amount:{
        type:String
    }
});