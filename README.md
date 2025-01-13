# ExamDesk

**ExamDesk** is a powerful tool designed to automate the tedious process of managing exams. It helps educational institutions generate exam timetables, seating arrangements, and invigilator assignments in seconds.

This project combines a modern UI built with [Svelte](https://svelte.dev/), robust logic implemented in Java, and desktop packaging with [Electron](https://www.electronjs.org/).

---

## 🚀 Features

- **Exam Timetable Generation**: Automatically create conflict-free timetables based on predefined rules.
- **Seating Arrangement**: Optimize seating plans for exam halls to ensure fairness and minimize errors.
- **Invigilator Assignment**: Assign invigilators efficiently, considering hall capacities and teacher availability.
- **Cross-Platform Support**: Works on Windows, macOS, and Linux.

---

## 🛠️ Technology Stack

- **Frontend**: [Svelte](https://svelte.dev/) for a responsive and user-friendly interface.
- **Backend**: Java for generating timetables, seating arrangements, and invigilator assignments.
- **Desktop Packaging**: [Electron](https://www.electronjs.org/) for cross-platform application delivery.

---

## 🖥️ Installation

1. Download the `.exe` file from the [Releases](https://github.com/your-username/ExamDesk/releases) section.
2. Install the application by running the `.exe` file.
3. Open the app and get started.

---

## 📂 Folder Structure

- **`electron`**: Electron packaging and routing-related code.
  - **`electron/functions`**: Contains all Java code and stores required files for the Java logic.
- **`public`**: Images and font files for the UI.
- **`src`**: Main UI code written in Svelte.

---

## 📝 How to Use

1. Open the app.
2. Select the file type you want to generate (e.g., Timetable, Seating, or Invigilator Assignment).
3. Upload the required files and click the **Generate** button.
4. *(Optional)* Adjust parameters in the side menu as needed.
5. Once the file is generated:
   - Click **Preview** to review the output.
   - Export the file to save it locally.

---

## 📜 License

This project is licensed under the [MIT License](LICENSE).
