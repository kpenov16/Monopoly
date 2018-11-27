package dk.monopoly.common;

import dk.monopoly.entities.Field;
import dk.monopoly.gateways.FieldGateway;

import java.util.ArrayList;
import java.util.List;

public class FieldGatewayImpl implements FieldGateway {
    private List<Field> fields = new ArrayList<>();

    @Override
    public Field getFieldByIndex(int fieldIndex) {
        return fields.get(fieldIndex);
    }

    @Override
    public void addField(Field field) {
        fields.add(field);
    }
}
