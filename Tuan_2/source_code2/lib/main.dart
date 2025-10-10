import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Thực hành 02',
      debugShowCheckedModeBanner: false,
      home: EmailCheckScreen(),
    );
  }
}

class EmailCheckScreen extends StatefulWidget {
  @override
  _EmailCheckScreenState createState() => _EmailCheckScreenState();
}

class _EmailCheckScreenState extends State<EmailCheckScreen> {
  final TextEditingController _emailController = TextEditingController();
  String _message = "";

  void _kiemTraEmail() {
    String email = _emailController.text.trim();

    setState(() {
      if (email.isEmpty) {
        _message = "Email không hợp lệ";
      } else if (!email.contains("@")) {
        _message = "Email không đúng định dạng";
      } else {
        _message = "Bạn đã nhập email hợp lệ";
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(24.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const Text(
                "Thực hành 02",
                style: TextStyle(fontSize: 22, fontWeight: FontWeight.bold),
              ),
              const SizedBox(height: 20),
              TextField(
                controller: _emailController,
                decoration: InputDecoration(
                  labelText: "Email",
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(12),
                  ),
                ),
              ),
              const SizedBox(height: 10),
              Text(
                _message,
                style: TextStyle(
                  color: _message == "Bạn đã nhập email hợp lệ"
                      ? Colors.green
                      : Colors.red,
                  fontSize: 16,
                ),
              ),
              const SizedBox(height: 15),
              ElevatedButton(
                onPressed: _kiemTraEmail,
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.blue,
                  padding:
                      const EdgeInsets.symmetric(horizontal: 40, vertical: 12),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(20),
                  ),
                ),
                child: const Text("Kiểm tra", style: TextStyle(fontSize: 16)),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
