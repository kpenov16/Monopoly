package dk.monopoly.gateways;

import dk.monopoly.entities.Field;

public interface FieldGateway {
    Field getFieldByIndex(int fieldIndex);
    void addField(Field field);
}
