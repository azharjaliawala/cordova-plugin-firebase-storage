var exec = require("cordova/exec");
var PLUGIN_NAME = "FirebaseStorage";

module.exports = {
    getIdToken: function (picUrl, picName) {
        return new Promise(function (resolve, reject) {
            exec(resolve, reject, PLUGIN_NAME, "getIdToken", [picUrl, picName]);
        });
    }
};
