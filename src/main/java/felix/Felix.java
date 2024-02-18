package felix;

import notes.NoteList;
import util.Parser;
import util.Storage;
import util.Ui;

/**
 * Main class for the felix.Duke chatbot application.
 * Handle user commands for managing tasks and provides feedback.
 * Some Javadocs generated by <a href="https://chat.openai.com/">...</a>
 *
 * @author Tan Qin Yong
 */

public class Felix {

    private Storage storage;
    private TaskList taskList;
    private Ui ui;
    private Parser parser;
    private NoteList noteList;

    public Felix() {
        ui = new Ui();
        storage = new Storage();
        parser = new Parser();
        taskList = storage.loadFile();
        noteList = storage.loadNotes();
    }

    public String run(String userInput) {
        String fullCommand = userInput;
        String[] commandArr = fullCommand.split(" ");
        String command = commandArr[0].toLowerCase();

        // return this string
        String toPrint = "";
        switch (command) {
        case "bye": {
            toPrint += parser.parseByeCommand(ui, storage, taskList);
            break;
        }
        case "list": {
            toPrint += parser.parseListCommand(ui, taskList);
            break;
        }
        case "delete": {
            toPrint += parser.parseDeleteCommand(ui, storage, taskList, commandArr);
            break;
        }
        case "find": {
            toPrint += parser.parseFindCommand(ui, taskList, commandArr);
            break;
        }
        case "mark": {
            toPrint += parser.parseMarkCommand(ui, storage, taskList, commandArr);
            break;
        }
        case "unmark": {
            toPrint += parser.parseUnmarkCommand(ui, storage, taskList, commandArr);
            break;
        }
        case "deadline": {
            toPrint += parser.parseDeadlineCommand(ui, storage, taskList, fullCommand);
            break;
        }
        case "event": {
            toPrint += parser.parseEventCommand(ui, storage, taskList, fullCommand);
            break;
        }
        case "todo": {
            toPrint += parser.parseToDoCommand(ui, storage, taskList, fullCommand);
            break;
        }
        case "note": {
            toPrint += parser.parseNoteCommand(ui, storage, noteList, commandArr, fullCommand);
            break;
        }
        case "remove": {
            toPrint += parser.parseRemoveCommand(ui, storage, noteList, commandArr);
            break;
        }
        case "notes": {
            toPrint += parser.parseNotesCommand(ui, storage, noteList, commandArr);
            break;
        }
        default: {
            toPrint += ui.printUnknown();
        }
        }
        return toPrint;
    }

    public String getResponse(String input) {
        String response = run(input);
        return "Felix : \n" + response;
    }

}