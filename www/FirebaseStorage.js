var exec = require("cordova/exec");
var PLUGIN_NAME = "FirebaseStorage";

module.exports = {
    uploadPicture: function (fileUri, filePath) {
        return new Promise(function (resolve, reject) {
            exec(resolve, reject, PLUGIN_NAME, "uploadPicture", [fileUri, filePath]);
        });
    }
};
