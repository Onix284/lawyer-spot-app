const { signUpUser } = require('../models/userModel');
const cloudinary = require('../config/cloudinary');


exports.signup = async (req, res) => {

    const { name, email, password, role} = req.body;

    if(!name || !email || !password || !role) {
        return res.status(400).json({ error: 'All fields are required' });
    }

    // However, cloudinary.uploader.upload_stream uses a stream,
    // so instead use a Promise wrapper:

    const uploadFromBuffer = (buffer) =>
      new Promise((resolve, reject) => {
        const stream = cloudinary.uploader.upload_stream(
          { folder: 'lawyer_spot_profile_images' },
          (error, result) => {
            if (error) reject(error);
            else resolve(result);
          }
        );
        stream.end(buffer);
      });

    const result = await uploadFromBuffer(req.file.buffer);

    // Now you have the Cloudinary URL:
    const profileImageURL = result.secure_url;

    signUpUser(name, email, password, profileImageURL, role, (err, result) => {
        if(err){
            if(err.code === 'ER_DUP_ENTRY') {
                return res.status(409).json({ message: 'Email already exists' });
            }
            return res.status(500).json({ message: 'Server error', error: err });
        }
        res.status(201).json({ message: 'User registered successfully', userId: result.insertId });
    });
}