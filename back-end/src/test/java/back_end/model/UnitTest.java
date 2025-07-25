package back_end.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;

public class UnitTest {
    @Test
	void convertTeaspoonToTablespoon() {
        Unit oneTeaspoon = Unit.TEASPOON;
        oneTeaspoon.setAmount(1);

        assertThat(oneTeaspoon.convertToTablespoon()).isInstanceOf(Unit.TABLESPOON.getClass());
        assertThat(oneTeaspoon.convertToTablespoon().getAmount()).isEqualTo(0.33, withPrecision(0.01));
	}

    @Test
	void convertTeaspoonToCup() {
        Unit oneTeaspoon = Unit.TEASPOON;
        oneTeaspoon.setAmount(1);

        assertThat(oneTeaspoon.convertToCup()).isInstanceOf(Unit.CUP.getClass());
        assertThat(oneTeaspoon.convertToCup().getAmount()).isEqualTo(0.0208, withPrecision(0.001));
	}

    @Test
	void convertTeaspoonToTeaspoon() {
        Unit oneTeaspoon = Unit.TEASPOON;
        oneTeaspoon.setAmount(1);

        assertThat(oneTeaspoon.convertToTeaspoon()).isInstanceOf(Unit.TEASPOON.getClass());
        assertThat(oneTeaspoon.convertToTeaspoon().getAmount()).isEqualTo(1);
	}

    @Test
	void convertTablespoonToTablespoon() {
        Unit oneTablespoon = Unit.TABLESPOON;
        oneTablespoon.setAmount(1);

        assertThat(oneTablespoon.convertToTablespoon()).isInstanceOf(Unit.TABLESPOON.getClass());
        assertThat(oneTablespoon.convertToTablespoon().getAmount()).isEqualTo(1);
	}

    @Test
	void convertTablespoonToCup() {
        Unit oneTablespoon = Unit.TABLESPOON;
        oneTablespoon.setAmount(1);

        assertThat(oneTablespoon.convertToCup()).isInstanceOf(Unit.CUP.getClass());
        assertThat(oneTablespoon.convertToCup().getAmount()).isEqualTo(0.0625);
	}

    @Test
	void convertTablespoonToTeaspoon() {
        Unit oneTablespoon = Unit.TABLESPOON;
        oneTablespoon.setAmount(1);

        assertThat(oneTablespoon.convertToTeaspoon()).isInstanceOf(Unit.TEASPOON.getClass());
        assertThat(oneTablespoon.convertToTeaspoon().getAmount()).isEqualTo(3);
	}

    @Test
	void convertCupToTablespoon() {
        Unit oneCup = Unit.CUP;
        oneCup.setAmount(1);

        assertThat(oneCup.convertToTablespoon()).isInstanceOf(Unit.TABLESPOON.getClass());
        assertThat(oneCup.convertToTablespoon().getAmount()).isEqualTo(16);
	}

    @Test
	void convertCupToCup() {
        Unit oneCup = Unit.CUP;
        oneCup.setAmount(1);

        assertThat(oneCup.convertToCup()).isInstanceOf(Unit.CUP.getClass());
        assertThat(oneCup.convertToCup().getAmount()).isEqualTo(1);
	}

    @Test
	void convertCupToTeaspoon() {
        Unit oneCup = Unit.CUP;
        oneCup.setAmount(1);

        assertThat(oneCup.convertToTeaspoon()).isInstanceOf(Unit.TEASPOON.getClass());
        assertThat(oneCup.convertToTeaspoon().getAmount()).isEqualTo(48);
	}
}
