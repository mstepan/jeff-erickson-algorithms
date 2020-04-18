package com.max.erickson.backtracking;

import org.junit.Test;

import java.util.Set;

import static com.max.erickson.backtracking.TextSegmentation.countSegmentations;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class TextSegmentationTest {

    @Test
    public void mixedCaseLetters() {
        int actualCount = countSegmentations("BOTHEARTHANDSATURNSPIN".toLowerCase(),
                                             Set.of("BOTH", "eArTH", "and", "SATURN", "SPIN",
                                                    "BOT", "HEART", "HANDS", "aT", "URNS", "PIN"));
        assertThat(actualCount).isEqualTo(2);
    }

    @Test
    public void normalCase() {
        int actualCount = countSegmentations("artistoil", Set.of("artist", "oil", "art", "is", "toil"));
        assertThat(actualCount).isEqualTo(2);
    }

    @Test
    public void mismatchAtTheEnd() {
        int actualCount = countSegmentations("artistoil", Set.of("artist", "ois", "art", "is", "toit"));
        assertThat(actualCount).isEqualTo(0);
    }

    @Test
    public void emptyTextShouldAlwaysReturnOne() {
        assertThat(countSegmentations("".toLowerCase(), Set.of("at", "both"))).isEqualTo(1);
        assertThat(countSegmentations("".toLowerCase(), Set.of())).isEqualTo(1);
    }

    @Test
    public void emptyDictionaryShouldNotFindAnything() {
        int actualCount = countSegmentations("bothat".toLowerCase(), Set.of(""));
        assertThat(actualCount).isEqualTo(0);
    }

    @Test
    public void withNullTextShouldFail() {
        assertThatThrownBy(() -> countSegmentations(null, Set.of("artist", "oil", "art", "is", "toil"))).
                isInstanceOf(NullPointerException.class).hasMessage("null 'text' parameter passed");

    }

    @Test
    public void withNullDictionaryShouldFail() {
        assertThatThrownBy(() -> countSegmentations("artistoil", null)).
                isInstanceOf(NullPointerException.class).hasMessage("null 'dictionary' parameters passed");

    }
}
