package com.example.dz

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WaterTrackerTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // 1. Проверка наличия и работы кнопки добавления жидкости
    @Test
    fun waterButton_adds250ml() {
        composeTestRule.setContent {
            WaterTracker()
        }

        // Проверяем, что кнопка "+250 мл" отображается
        composeTestRule.onNodeWithText("+250 мл").assertIsDisplayed()

        // Проверяем начальное значение 0 мл
        composeTestRule.onNodeWithText("0 мл").assertIsDisplayed()

        // Нажимаем кнопку один раз — должно стать 250 мл
        composeTestRule.onNodeWithText("+250 мл").performClick()
        composeTestRule.onNodeWithText("250 мл").assertIsDisplayed()

        // Нажимаем ещё раз — должно стать 500 мл
        composeTestRule.onNodeWithText("+250 мл").performClick()
        composeTestRule.onNodeWithText("500 мл").assertIsDisplayed()
    }

    // 2. Проверка наличия и работы кнопки «Завершить день»
    @Test
    fun endDayButton_resetsWaterCounter() {
        composeTestRule.setContent {
            WaterTracker()
        }

        // Проверяем, что кнопка «Завершить день» отображается
        composeTestRule.onNodeWithText("Завершить день").assertIsDisplayed()

        // Добавляем 250 мл
        composeTestRule.onNodeWithText("+250 мл").performClick()
        composeTestRule.onNodeWithText("250 мл").assertIsDisplayed()

        // Нажимаем «Завершить день» — счётчик воды должен сброситься
        composeTestRule.onNodeWithText("Завершить день").performClick()
        composeTestRule.onNodeWithText("0 мл").assertIsDisplayed()
    }

    // 3. Проверка текста с количеством дней подряд
    @Test
    fun daysCounter_displaysCorrectly() {
        composeTestRule.setContent {
            WaterTracker()
        }

        // Проверяем начальный текст
        composeTestRule.onNodeWithText("Дней подряд (1500+ мл): 0").assertIsDisplayed()

        // Нажимаем кнопку воды 6 раз (6 * 250 = 1500 мл)
        repeat(6) {
            composeTestRule.onNodeWithText("+250 мл").performClick()
        }
        composeTestRule.onNodeWithText("1500 мл").assertIsDisplayed()

        // Завершаем день — счётчик дней должен стать 1
        composeTestRule.onNodeWithText("Завершить день").performClick()
        composeTestRule.onNodeWithText("Дней подряд (1500+ мл): 1").assertIsDisplayed()

        // Теперь выпиваем только 250 мл (меньше 1500)
        composeTestRule.onNodeWithText("+250 мл").performClick()
        composeTestRule.onNodeWithText("250 мл").assertIsDisplayed()

        // Завершаем день — счётчик дней должен обнулиться
        composeTestRule.onNodeWithText("Завершить день").performClick()
        composeTestRule.onNodeWithText("Дней подряд (1500+ мл): 0").assertIsDisplayed()
    }
}