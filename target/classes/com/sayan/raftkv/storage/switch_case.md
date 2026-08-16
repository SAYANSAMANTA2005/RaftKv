    switch (command.getType()) {

        case SET:
            store.put(command.getKey(), command.getValue());
            break;

        case DELETE:
            store.remove(command.getKey());
            break;

        default:
            throw new IllegalArgumentException(
                    "Unknown command: " + command.getType());
    }