package command;

public enum CommandFactory {
    INSTANCE;

    public Command getCommand(String command){
        switch (command) {
            case Commands.SIGN_IN:
                return new SignInCommand();
            case Commands.LOG_IN:
                return new LogInCommand();
            case Commands.LOG_OUT:
                return new LogOutCommand();
            case Commands.DELETE_USER:
                return new SignInCommand();
            default:
                throw new IllegalArgumentException();
        }
    }
}
