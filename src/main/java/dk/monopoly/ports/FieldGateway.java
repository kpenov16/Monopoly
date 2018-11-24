package dk.monopoly.ports;

public interface FieldGateway {
    public Field getFieldByIndex(int fieldIndex);
    public void addField(Field field);
}
