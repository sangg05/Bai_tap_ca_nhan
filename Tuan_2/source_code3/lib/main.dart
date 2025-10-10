import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Thực hành 01',
      debugShowCheckedModeBanner: false,
      home: AgeCheckScreen(),
    );
  }
}

class AgeCheckScreen extends StatefulWidget {
  @override
  _AgeCheckScreenState createState() => _AgeCheckScreenState();
}

class _AgeCheckScreenState extends State<AgeCheckScreen> {
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _ageController = TextEditingController();
  String _result = "";

  void _kiemTraTuoi() {
    String name = _nameController.text.trim();
    String ageText = _ageController.text.trim();

    if (name.isEmpty || ageText.isEmpty) {
      setState(() {
        _result = "Vui lòng nhập đầy đủ họ tên và tuổi!";
      });
      return;
    }

    int? age = int.tryParse(ageText);
    if (age == null || age < 0) {
      setState(() {
        _result = "Tuổi không hợp lệ!";
      });
      return;
    }

    String type = "";
    if (age > 65) {
      type = "Người già";
    } else if (age > 6) {
      type = "Người lớn";
    } else if (age >= 2) {
      type = "Trẻ em";
    } else {
      type = "Em bé";
    }

    setState(() {
      _result = "$name là $type (${age.toString()} tuổi)";
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
                "THỰC HÀNH 01",
                style: TextStyle(fontSize: 22, fontWeight: FontWeight.bold),
              ),
              const SizedBox(height: 30),
              Container(
                padding: const EdgeInsets.all(16),
                decoration: BoxDecoration(
                  color: Colors.grey[200],
                  borderRadius: BorderRadius.circular(12),
                ),
                child: Column(
                  children: [
                    TextField(
                      controller: _nameController,
                      decoration: const InputDecoration(labelText: "Họ và tên"),
                    ),
                    TextField(
                      controller: _ageController,
                      decoration: const InputDecoration(labelText: "Tuổi"),
                      keyboardType: TextInputType.number,
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: _kiemTraTuoi,
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.blue,
                  padding: const EdgeInsets.symmetric(
                    horizontal: 40,
                    vertical: 12,
                  ),
                ),
                child: const Text("Kiểm tra", style: TextStyle(fontSize: 16)),
              ),
              const SizedBox(height: 20),
              Text(
                _result,
                style: TextStyle(
                  color: _result.contains("hợp lệ") ? Colors.green : Colors.red,
                  fontSize: 16,
                  fontWeight: FontWeight.w500,
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
