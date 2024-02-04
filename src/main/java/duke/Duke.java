package duke;

import task.Deadline;
import task.Event;
import task.ToDo;
import util.Parser;
import util.Storage;
import util.Ui;

import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Main class for the duke.Duke chatbot application.
 * Handle user commands for managing tasks and provides feedback.
 * Some Javadocs generated by https://chat.openai.com/
 *
 * @author Tan Qin Yong
 */

public class Duke {

    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.printGreeting();

        Storage storage = new Storage();
        Parser parser = new Parser();

        Scanner sc = new Scanner(System.in);

        TaskList taskList = storage.loadFile();
        boolean exit = false;

        while (!exit) {
            String fullCommand = sc.nextLine();
            String[] commandArr = fullCommand.split(" ");
            String command = commandArr[0].toLowerCase();

            switch (command) {
            case "bye": {
                ui.printBye();
                exit = true;
                storage.saveToFile(taskList);
                break;
            }
            case "list": {
                ui.printLine();
                taskList.printAllTasks();
                ui.printLine();
                break;
            }
            case "delete": {
                ui.printLine();
                try {
                    int taskNo = Integer.parseInt(commandArr[1]);
                    taskList.deleteTask(taskNo);
                } catch (NumberFormatException | NullPointerException | IndexOutOfBoundsException e) {
                    ui.printOperationError(e);
                    break;
                }

                storage.saveToFile(taskList);
                ui.printLine();
                break;
            }
            case "mark": {
                ui.printLine();
                ui.printMark();
                try {
                    int taskNo = Integer.parseInt(commandArr[1]);
                    taskList.markDoneAtInd(taskNo);
                } catch (NumberFormatException | NullPointerException | IndexOutOfBoundsException e) {
                    ui.printOperationError(e);
                    break;
                }

                storage.saveToFile(taskList);
                ui.printLine();
                break;
            }
            case "unmark": {
                ui.printLine();
                ui.printUnmark();
                try {
                    int taskNo = Integer.parseInt(commandArr[1]);
                    taskList.markNotDoneAtInd(taskNo);
                } catch (NumberFormatException | NullPointerException | IndexOutOfBoundsException e) {
                    ui.printOperationError(e);
                    break;
                }

                storage.saveToFile(taskList);
                ui.printLine();
                break;
            }
            case "deadline": {
                ui.printLine();
                String[] splitCommand = parser.parseDeadline(fullCommand);
                try {
                    String taskDescription = splitCommand[0];
                    if (taskDescription.isEmpty()) {
                        ui.printEmptyDescription();
                        break;
                    }
                    String byDate = splitCommand[1];
                    Deadline dl = new Deadline(taskDescription, parser.parseDate(byDate));
                    taskList.addTask(dl, false);
                } catch (IndexOutOfBoundsException | IllegalArgumentException | DateTimeParseException e) {
                    ui.printDeadlineError(e);
                    break;
                }

                storage.saveToFile(taskList);
                ui.printLine();
                break;
            }
            case "event": {
                ui.printLine();
                String[] splitCommand = parser.parseEvent(fullCommand);
                try {
                    String taskDescription = splitCommand[0];
                    if (taskDescription.isEmpty()) {
                        ui.printEmptyDescription();
                        break;
                    }
                    String from = splitCommand[1];
                    String to = splitCommand[2];
                    Event event = new Event(taskDescription, parser.parseDate(from), parser.parseDate(to));
                    taskList.addTask(event, false);
                } catch(IndexOutOfBoundsException | IllegalArgumentException | DateTimeParseException e) {
                    ui.printEventError(e);
                    break;
                }

                storage.saveToFile(taskList);
                ui.printLine();
                break;
            }
            case "todo": {
                ui.printLine();
                fullCommand = parser.parseToDo(fullCommand);
                if (fullCommand.isEmpty()) {
                    ui.printEmptyDescription();
                    break;
                }

                ToDo toDo = new ToDo(fullCommand);
                taskList.addTask(toDo, false);
                storage.saveToFile(taskList);
                ui.printLine();
                break;
            }
            default: {
                ui.printUnknown();
            }
            }
        }
    }

}