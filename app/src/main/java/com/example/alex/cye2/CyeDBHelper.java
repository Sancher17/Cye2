package com.example.alex.cye2;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CyeDBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 3;
    public static final String DB_NAME = "CYE";
    public static final String TABLE_EXPENSE = "expense";
    public static final String TABLE_EXPENSE_CATEGORY = "expense_category";
    public static final String KEY_DATE = "date";
    public static final String KEY_NAME = "name";
    public static final String KEY_SUM = "sum";

    public CyeDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        // int oldVersion - текущая версия БД
        // int newVersion - новая версия из помощника SQLite  - DB_VERSION
        if (oldVersion < 2) {
           // db.execSQL("DROP TABLE "+ TABLE_EXPENSE);
           // db.execSQL("DROP TABLE " + TABLE_EXPENSE_CATEGORY);
        }

        if (oldVersion < 2){
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_EXPENSE + " (_id integer primary key autoincrement, date TEXT, name TEXT, sum INTEGER)");
            insertExpense(db, "1980", "Alex", 50);
            insertExpense(db, "2000", "King", 150);
            insertExpense(db, "3000", "Poll", 25);
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_EXPENSE_CATEGORY + " (_id integer primary key autoincrement, name TEXT)");
            insertExpenseCategory(db, "Магазин");
        }


        /*Обновление UPDATE строк*/
        //ContentValues values = new ContentValues();
        //values.put(KEY_DATE, "2222"); //в столбец DATE поместить 2222
        //db.update(TABLE_EXPENSE, values, "name = ?", new String[] {"Alex"});//где NAME = Alex
        //1-ый парам - TABLE_EXPENSE - содержит имя таблицы
        //2-ой парам - values - указывает какие значения должны измениться
        //3-ий парам - "name = ?" - задает условия отбора обновляемых записей
        //4-ый парам - new String[] {"Alex"} - символ ? обозначает значение столбца которое
        //определяется содержимым последнего параметра  - "Alex"
        /*Сложные условия*/
        //db.update(TABLE_EXPENSE, values, "name = ? or date = ?", new String[] {"Alex"});
        //- "name = ? or date = ?" - фильтрует по 2 -ум столбцам ИЛИ
        //db.update(TABLE_EXPENSE, values, "_id = ?", new String[] {Integer.toString(2)});
        //тут необходимо преобразовать Integer к String.
        /*УДАЛЕНИЕ delete()*/
        //удалить строку Alex
        //db.delete(TABLE_EXPENSE, "name = ?", new String[] {"Alex"});
    }

    public static void insertExpense(SQLiteDatabase db, String date, String name, int sum) {
        ContentValues expenseValues = new ContentValues();
        expenseValues.put(KEY_DATE, date);
        expenseValues.put(KEY_NAME, name);
        expenseValues.put(KEY_SUM, sum);
        db.insert(TABLE_EXPENSE, null, expenseValues);
    }

    public static void insertExpenseCategory(SQLiteDatabase db, String name) {
        ContentValues expenseValues = new ContentValues();
        expenseValues.put(KEY_NAME, name);
        db.insert(TABLE_EXPENSE_CATEGORY, null, expenseValues);
    }


}

// место хранения БД /data/data/com.hfad.cye/databases
//
// INTEGER Любое целое число
// TEXT Любые символьные данные
// REAL Любое вещественное чилсо
// NUMERIC Логическое значение, дата, дата время
// BLOB Двоичные большие объекты
//
//добавить столбец - синтаксис  ("________;");
//    ("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
//переименование таблицы
//    ALTER TABLE DRINK RENAME TO FOOD
//удаление
//    DROP TABLE DRINK
/*Метод onCreate() вызывается при создании базы данных Помощник SQLite отвечает за то,
 чтобы база данных SQLite была создана  в момент ее первого использования. Сначала на устройстве создается
 пустая база данных, после чего вызывается метод onCreate() помощника SQLite.
  При вызове метода onCreate() передается объект SQLiteDatabase.
  --------------------------------------------
Когда возникает необходимость в изменении структуры базы данных приложения, приходится учитывать два
основных сценария. Первый — пользователь еще не устанавливал ваше приложение, и база данных на его
устройстве не создавалась. В этом случае помощник SQLite создает базу данных при первом обращении
к базе данных и выполняет метод onCreate(). Второй — пользователь устанавливает новую версию приложения
 с другой версией базы данных. Если помощник SQLite обнаруживает, что установленная база данных не
 соответствует текущей версии приложения, вызывается метод onUpgrade() или onDowngrade().

 Чтобы определить, нуждается ли база данных SQLite в обновлении, помощник SQLite проверяет ее номер версии.
 Номер версии присваивается базе данных в помощнике SQLite — он передается при вызове конструктора
 суперкласса SQLiteOpenHelper.

 Когда пользователь устанавливает новейшую версию приложения на своем устройстве и приложение в
 первый раз обращается к базе данных, помощник SQLite сравнивает свой номер версии с номером версии
 базы данных на устройстве. Если номер версии в коде помощника SQLite выше номера версии базы, вызывается
 метод onUpgrade() помощника SQLite. Если номер версии в коде помощника SQLite ниже номера версии базы,
  вместо этого вызывается метод onDowngrade(). После выполнения одного из этих методов номер версии
  базы данных заменяется номером версии из кода помощника SQLite.

Обновление базы данных
Предположим, мы хотим обновить структуру базы данных с добавлением нового столбца в таблицу DRINK.
Так как изменения должны распространяться как на новых, так и на существующих пользователей,
соответствующий код должен быть включен как в метод onCreate(), так и в метод onUpgrade().
Метод onCreate() гарантирует, что новый столбец будет присутствовать у всех новых пользователей,
а метод onUpgrade() позаботится о том, чтобы он был и у всех существующих пользователей. Вместо
того, чтобы повторять похожий код в методах onCreate() и onUpgrade(), мы создадим отдельный метод
updateMyDatabase(), который будет вызываться из onCreate() и onUpgrade(). Код, в настоящее время
находящийся в onCreate(), будет перемещен в новый метод updateMyDatabase(), и к нему добавится код
 создания дополнительного столбца. При таком подходе весь код базы данных будет храниться в одном
 месте, а нам будет проще управлять изменениями, вносимыми при каждом обновлении базы.

 ---------------------------------------------

  */


/*Cвалка*/
//вариант 1
//        insertDrink(db, "Latte", "Вкусный кофе с большим количеством молока", R.drawable.tea_m);
//        insertDrink(db, "Cappuccino", "Вкусный кофе с малым количеством молока", R.drawable.tea_s);
//        insertDrink(db, "Lemon", "Вкусный кофе без молока, но с лимоном", R.drawable.tea_l);

//вариант 2
//        db.execSQL("CREATE TABLE IF NOT EXISTS users (name TEXT, age INTEGER)");
//        db.execSQL("INSERT INTO users VALUES ('Tom Smith', 23);");
//        db.execSQL("INSERT INTO users VALUES ('John Dow', 31);");
//

