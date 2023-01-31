package helpers;

import duke.exceptions.OutOfIndexException;
import duke.exceptions.VagueInputException;
import duke.exceptions.WrongBooleanException;
import task.Deadline;
import task.Event;
import task.Task;
import task.Todo;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Handles input cases by the user.
 */
public class Parser {

    /**
     * Handles inputs, and outputs the according string.
     *
     * @param sc Scans the inputs of the user.
     * @param tasks Stores the Tasks objects into
     * @param pw Writes to the screen for the user to comprehend.
     */
    public static void handleInputs(Scanner sc, TaskList tasks, PrintWriter pw) {
        String input = sc.nextLine();
        while (!input.equals("bye")) {
            String[] splitArr = input.split(" ");
            try {
                if (input.equals("list")) {
                    System.out.println(Ui.formatStr(tasks.listThings()));
                } else if (splitArr.length == 1) {
                    throw new VagueInputException("Oh no! What do you mean? \n" +
                            "I'm confused. Please specify... @.@");  
                }
                else if (splitArr[0].equals("mark") || splitArr[0].equals("unmark")) {
                    if ((Integer.parseInt(splitArr[1])) > tasks.getSize()) {
                        throw new OutOfIndexException("Help! \n" +
                                "The number has to be within range of our task-list!\n" +
                                "try again.");
                    }
                    if (splitArr[0].equals("mark") &&
                            tasks.getTask(Integer.parseInt(splitArr[1]) - 1).
                                    getMark() != false) {
                        throw new WrongBooleanException("Hey! \n" +
                                "This is already done. You can't mark it again. :0 \n" +
                                "try again.");
                    } else if (splitArr[0].equals("unmark") &&
                            tasks.getTask(Integer.parseInt(splitArr[1]) - 1).
                                    getMark() != true) {
                        throw new WrongBooleanException("Hey! \n" +
                                "This is undone. You can't mark it undone again. :0 \n" +
                                "try again.");
                    }
                    tasks.mark(splitArr[0], (Integer.parseInt(splitArr[1]) - 1));
                } else if (splitArr[0].equals("delete")) {
                    if ((Integer.parseInt(splitArr[1])) > tasks.getSize()) {
                        throw new OutOfIndexException("Help! \n" +
                                "The number has to be within range of our task-list!\n" +
                                "Please try again!");
                    }
                    Task newTask = tasks.getTask(Integer.parseInt(splitArr[1]) - 1);
                    tasks.removeTask(Integer.parseInt(splitArr[1]) - 1);
                    System.out.println(Ui.formatStr(tasks.deleteReport(newTask)));
                } else if (splitArr[0].equals("find")) {
                    String searchTerm = input.substring(5);
                    boolean hasFoundAnyTerms = false;
                    String outputResults = "";
                    for (int i = 0; i < tasks.getSize(); i++) {
                        if (tasks.getTask(i).getContent().contains(searchTerm)) {
                            if (outputResults == "") {
                                outputResults += i + tasks.getTask(i).toString();
                                hasFoundAnyTerms = true;
                            } else {
                                outputResults += "\n" + i + tasks.getTask(i).toString();
                                hasFoundAnyTerms = true;
                            }
                        }
                    }
                    Ui.outputSearchResults(hasFoundAnyTerms, outputResults);
                } else if (splitArr[0].equals("todo")) {
                    Todo newTodo = new Todo(input);
                    tasks.addTask(newTodo);
                    System.out.println(Ui.formatStr(tasks.addReport(newTodo)));
                    pw.write(newTodo.printRecord());
                } else if (splitArr[0].equals("deadline")) {
                    Deadline newDead = new Deadline(input);
                    tasks.addTask(newDead);
                    System.out.println(Ui.formatStr(tasks.addReport(newDead)));
                    pw.write(newDead.printRecord());
                } else if (splitArr[0].equals("event")) {
                    Event newEvent = new Event(input);
                    tasks.addTask(newEvent);
                    System.out.println(Ui.formatStr(tasks.addReport(newEvent)));
                    pw.write(newEvent.printRecord());
                } else {
                    throw new VagueInputException("Oh no! What do you mean? \n" +
                            "I'm confused. Please specify... @.@");
                }
            } catch (VagueInputException ex) {
                System.out.println(Ui.formatStr(ex.getMessage()));
                input = sc.nextLine();
                continue;
            } catch (OutOfIndexException ex) {
                System.out.println(Ui.formatStr(ex.getMessage()));
                input = sc.nextLine();
                continue;
            } catch (WrongBooleanException ex) {
                System.out.println(Ui.formatStr(ex.getMessage()));
                input = sc.nextLine();
                continue;
            }
            input = sc.nextLine();
        }
    }
}
