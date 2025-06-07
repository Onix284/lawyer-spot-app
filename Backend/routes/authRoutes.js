const express = require('express');
const router = express.Router();
const { signup } = require('../controllers/authController');
const upload = require('../middleware/upload'); // multer middleware

router.post('/signup', upload.single('profileImage'), signup);

module.exports = router;
