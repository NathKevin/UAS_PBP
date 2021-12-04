package com.example.uts;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.example.uts.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class PengantarFormTest {

    @Rule
    public ActivityTestRule<PengantarForm> mActivityTestRule = new ActivityTestRule<>(PengantarForm.class);

    @Test
    public void pengantarFormTest() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.btnAddPengantar), withText("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.etNamaPengantar),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_name),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("Kevvin"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btnAddPengantar), withText("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.etNamaPengantar), withText("Kevvin"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_name),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText(""));

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.etNamaPengantar),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_name),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.etNoTelpPengantar),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_noTelp),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("089528009832"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btnAddPengantar), withText("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.etNoTelpPengantar), withText("089528009832"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_noTelp),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText5.perform(replaceText("08952800"));

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.etNoTelpPengantar), withText("08952800"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_noTelp),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText6.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.etNamaPengantar),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_name),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText7.perform(replaceText("Kevin"), closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.btnAddPengantar), withText("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.etNoTelpPengantar), withText("08952800"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_noTelp),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText8.perform(replaceText("123456789101"));

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.etNoTelpPengantar), withText("123456789101"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_noTelp),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText9.perform(closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.btnAddPengantar), withText("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction textInputEditText10 = onView(
                allOf(withId(R.id.etNoTelpPengantar), withText("123456789101"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_noTelp),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText10.perform(replaceText("08952800983211"));

        ViewInteraction textInputEditText11 = onView(
                allOf(withId(R.id.etNoTelpPengantar), withText("08952800983211"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_noTelp),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText11.perform(closeSoftKeyboard());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.btnAddPengantar), withText("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction textInputEditText12 = onView(
                allOf(withId(R.id.etNoTelpPengantar), withText("08952800983211"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_noTelp),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText12.perform(click());

        ViewInteraction textInputEditText13 = onView(
                allOf(withId(R.id.etNoTelpPengantar), withText("08952800983211"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_noTelp),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText13.perform(replaceText("089528009832"));

        ViewInteraction textInputEditText14 = onView(
                allOf(withId(R.id.etNoTelpPengantar), withText("089528009832"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_noTelp),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText14.perform(closeSoftKeyboard());

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.btnAddPengantar), withText("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        materialButton7.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
