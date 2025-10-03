import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false, // tắt chữ DEBUG
      home: const ProfilePage(),
    );
  }
}

class ProfilePage extends StatelessWidget {
  const ProfilePage({super.key});

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
      body: SafeArea(
        child: Stack(
          children: [
            // nút back
            Positioned(
              top: 10,
              left: 10,
              child: IconButton(
                icon: const Icon(Icons.arrow_back),
                onPressed: () {
                  // Xử lý nút bấm
                },
              ),
            ),

            // Nút edit
            Positioned(
              top: 10,
              right: 10,
              child: IconButton(
                icon: const Icon(Icons.edit),
                onPressed: () {
                  //Xử lý khi bấm nút
                },
              ),
            ),

            //Nội dung chính
            Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: const [
                  CircleAvatar(
                    radius: 50,
                    backgroundImage: NetworkImage(
                      "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQuFob_7ZEq-gJSVHLVm6GgbUDuCgdWXQmsxg&s",
                    ),
                  ),
                  SizedBox(height: 20),
                  Text(
                    "Tuyết Sang",
                    style: TextStyle(fontSize: 22, fontWeight: FontWeight.bold),
                  ),
                  SizedBox(height: 5),
                  Text(
                    "Việt Nam, VN",
                    style: TextStyle(fontSize: 16, color: Colors.grey),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
