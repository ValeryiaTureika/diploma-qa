package ru.iteco.fmhandroid.ui.data;

import static ru.iteco.fmhandroid.ui.data.Helper.Rand.random;
import static ru.iteco.fmhandroid.ui.data.Helper.getCurrentDate;
import static ru.iteco.fmhandroid.ui.data.Helper.getCurrentTime;

import io.bloco.faker.Faker;

public class Resources {

    Faker faker = new Faker();

    // Новости
    int number = random(1, 2, 3, 4, 5);
    public String newsPublicationDate = getCurrentDate();
    public String dateForNonExistentNews = "25.09.2039";
    public String newsPublicationTime = getCurrentTime();
    public String newsDescriptionLatin = "News Description" + " " + number;
    public String newsDescriptionCyr = "Описание новости" + " " + number;
    public String newsDescriptionSymbols = "#$%^@#&((*";
    public String newsDescriptionSpace = " ";
    public String newsTitleLatin = "News Title";
    public String newsTitleCyr = "Название новости" + " " + number;
    public String newsTitleSymbols = "#$%^@#&((*";
    public String newsTitleSpace = " ";

    // Заявки
    public String claimPublicationDate = getCurrentDate();
    public String dateForNonExistentClaim = "08.12.2039";
    public String claimPublicationTime = getCurrentTime();
    public String claimDescriptionLatin = "Claim Description" + " " + number;
    public String claimDescriptionCyr = "Затопило";
    public String claimDescriptionSymbols = "#$%^@#&((*";
    public String claimDescriptionSpace = " ";
    public String claimTitleLatin = "Claim Title";
    public String claimTitle51 = "Здесь написан рандомный текст в колличестве символов больше 50";
    public String claimTitle50 = "Здесь написан рандомный текст в колличестве символ";
    public String claimTitleCyr = "Водоснабжение";
    public String claimTitleSymbols = "#$%^@#&((*";
    public String claimTitleSpace = " ";
    public String claimEditedTitle = "Новое название";
    public String claimEditedDescription = "Новое описание";

    // Комментарии
    public String commentLatin = "Comment" + " " + number;
    public String commentCyr = "Комментарий" + " " + number;
    public String commentSpace = " ";
    public String commentSymbols = "#$%^@#&((*";
    public String editedComment = "Отредактированный комментарий";
}
