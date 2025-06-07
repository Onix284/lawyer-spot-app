const cloudinary = require('cloudinary').v2;

cloudinary.config({
    cloud_name: 'drsaigsqw',
    api_key: '381764348935239',
    api_secret: '0oyaUVdQydsGdpQaYqNhA8NdlQw',
    secure: true
});

module.exports = cloudinary;