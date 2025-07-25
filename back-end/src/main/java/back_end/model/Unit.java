package back_end.model;

public enum Unit {
    TABLESPOON ("tablespoon") {
        @Override
        public Unit convertToTeaspoon() {
            Unit newUnit = Unit.TEASPOON;
            newUnit.setAmount(this.getAmount() *3);

            return newUnit;
        }

        @Override
        public Unit convertToCup() {
            Unit newUnit = Unit.CUP;
            newUnit.setAmount(this.getAmount() /16);

            return newUnit;
        }
    },
    TEASPOON ("teaspoon") {
        @Override
        public Unit convertToTablespoon() {
            Unit newUnit = Unit.TABLESPOON;
            newUnit.setAmount(this.getAmount() /3);

            return newUnit;
        }

        @Override
        public Unit convertToCup() {
            Unit newUnit = Unit.CUP;
            newUnit.setAmount(this.getAmount() /48);

            return newUnit;
        }
    },
    CUP ("cup") {
        @Override
        public Unit convertToTablespoon() {
            Unit newUnit = Unit.TABLESPOON;
            newUnit.setAmount(this.getAmount() *16);

            return newUnit;
        }

        @Override
        public Unit convertToTeaspoon() {
            Unit newUnit = Unit.TEASPOON;
            newUnit.setAmount(this.getAmount() *48);

            return newUnit;
        }
    };

    private double amount;
    private String name;

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Unit convertToTablespoon() {
        return this;
    }

    public Unit convertToTeaspoon() {
        return this;
    }

    public Unit convertToCup() {
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void reduceBy(double factor) {
        this.amount = this.amount / factor;
    }

    public void increaseBy(double factor) {
        this.amount = this.amount * factor;
    }

    Unit (String name) {
        this.name = name.toUpperCase();
    }
}
