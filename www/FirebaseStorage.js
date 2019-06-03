var exec = require("cordova/exec");
var PLUGIN_NAME = "FirebaseStorage";

module.exports = {
    uploadPicture: function (picUrl, picName) {
        return new Promise(function (resolve, reject) {
            exec(resolve, reject, PLUGIN_NAME, "uploadPicture", [picUrl, picName]);
        });
    }
};
