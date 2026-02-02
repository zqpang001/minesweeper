## Minesweeper Build & Deployment Guide
# Prerequisites
- Java JDK 17 or higher.
- Apache Maven installed.

## Build Instructions
1. Open your terminal in the project root
2. Run tests ensure code is working fine. "mvn test" (Validates game logic).
3. Clean & Build: "mvn clean package" in the terminal
4. Verify that the runnable .jar file in MineSweeper/target folder
5. Ready for deployment & execution

## To run the application directly in Windows
1. Open command prompt in window
2. Execute the JAR: "java -jar target/MineSweeper-1.0-SNAPSHOT.jar"