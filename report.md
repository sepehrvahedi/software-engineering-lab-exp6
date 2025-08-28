# گزارش تمرین الگوهای طراحی شی‌گرا و بازآرایی کد

## فاز اول: سیستم مدیریت پویای مصرف انرژی هوشمند

### پیاده‌سازی الگوهای طراحی

#### الگوی Strategy
برای مدیریت سیاست‌های محاسبه هزینه انرژی از الگوی Strategy استفاده شد. این الگو شامل موارد زیر است:
- **Interface Strategy:** `EnergyPricingStrategy` که متد `calculateCost(int units)` را تعریف می‌کند
- **Concrete Strategies:**
    - `StandardPricingStrategy`: محاسبه با نرخ ۵۰۰ تومان
    - `PeakHoursPricingStrategy`: محاسبه با نرخ ۱۰۰۰ تومان
    - `GreenModePricingStrategy`: محاسبه با نرخ ۳۰۰ تومان
- **Context:** کلاس `EnergyManager` که استراتژی فعلی را نگهداری و اجرا می‌کند

#### الگوی State
برای مدیریت حالات سیستم انرژی از الگوی State استفاده شد:
- **State Interface:** `SystemState` با متدهای `activate()`, `getStateName()`, `getConsumptionMultiplier()`
- **Concrete States:**
    - `ActiveState`: حالت فعال با ضریب مصرف ۱
    - `EcoModeState`: حالت اقتصادی با ضریب مصرف ۰.۵
    - `ShutdownState`: حالت خاموش با ضریب مصرف ۰
- **Context:** کلاس `EnergySystem` که حالت فعلی را مدیریت می‌کند

## فاز دوم: بازآرایی کد پروژه

### بازآرایی‌های انجام شده

#### ۱. اعمال الگوی Facade
**کلاس:** `DiagramManagerFacade`
کلاس Facade برای مدیریت پیچیدگی‌های داخلی سیستم UML ایجاد شد که عملیات مختلف مانند اعتبارسنجی، تولید XML و مدیریت وابستگی‌ها را به صورت یکپارچه ارائه می‌دهد.

#### ۲. استفاده از Polymorphism به جای شرط
**کلاس:** `LexicalAnalyzer`
switch-case بزرگ برای تبدیل نوع توکن‌ها به سلسله مراتبی از کلاس‌های `TokenHandler` تبدیل شد که هر کدام نوع خاصی از توکن را پردازش می‌کنند.

#### ۳. Separate Query From Modifier
**کلاس:** `ClassDiagram`
متدهای `getClasses()` و `allClassNames()` به عنوان Query و متدهای `setClasses()` و `addClass()` به عنوان Modifier جداسازی شدند.

#### ۴. Self Encapsulated Field
**کلاس:** `ClassAttribute`
دسترسی مستقیم به فیلدهای `name` و `valueType` با متدهای getter و setter جایگزین شد.

#### ۵. Extract Method
**کلاس:** `ClassConstructor`  
متدهای کمکی `validateParameters()` و `generateParameterString()` از متد اصلی `equals()` استخراج شدند.

#### ۶. Replace Magic Numbers with Named Constants
**کلاس:** `DiagramGetter`
اعداد ثابت مانند ۶۰۰، ۱۰، ۴۰۰ با ثابت‌های نام‌دار مانند `DEFAULT_BUTTON_WIDTH`، `DEFAULT_ZOOM_STEP` جایگزین شدند.

#### ۷. Remove Dead Code
**کلاس:** `DiagramGetter`
متغیر `Graphics graphics = new DebugGraphics()` که استفاده نمی‌شد حذف گردید.

## پاسخ سوالات

### ۱. دسته‌بندی الگوهای طراحی GoF

**الگوهای ایجادی (Creational Patterns):** این الگوها با نحوه ایجاد اشیاء سروکار دارند و مکانیسم‌های ایجاد انعطاف‌پذیر را فراهم می‌کنند.
مثال‌ها: Factory Method، Singleton، Builder، Abstract Factory

**الگوهای ساختاری (Structural Patterns):** این الگوها نحوه ترکیب کلاس‌ها و آبجکت‌ها را برای تشکیل ساختارهای بزرگ‌تر تعریف می‌کنند.
مثال‌ها: Facade، Adapter، Decorator، Composite

**الگوهای رفتاری (Behavioral Patterns):** این الگوها با الگوریتم‌ها و تخصیص مسئولیت‌ها بین آبجکت‌ها سروکار دارند.
مثال‌ها: Strategy، State، Observer، Command

### ۲. دسته‌بندی الگوهای فاز اول
الگوهای Strategy و State که در فاز اول استفاده شدند، هر دو جزو دسته **الگوهای رفتاری (Behavioral Patterns)** هستند.

### ۳. مناسب‌ترین الگوی طراحی برای سیستم انرژی

**برای مدیریت حالات سیستم:** الگوی **State** مناسب‌تر است زیرا سیستم در هر لحظه دقیقاً در یکی از سه حالت قرار دارد و رفتار سیستم بسته به حالت فعلی متفاوت است.

**برای مدیریت سیاست‌های محاسبه:** الگوی **Strategy** مناسب‌تر است زیرا الگوریتم محاسبه هزینه در زمان اجرا قابل تغییر است بدون تغییر کد اصلی.

**نحوه پیاده‌سازی:**
- تغییر وضعیت: `energySystem.changeState(new EcoModeState())`
- تغییر سیاست: `energyManager.setPricingStrategy(new PeakHoursPricingStrategy())`
- مشاهده وضعیت: `energySystem.getCurrentState().getStateName()`
- محاسبه هزینه: `energyManager.calculateTotalCost(units)`

### ۴. اصول SOLID در Factory Method

**SRP (Single Responsibility Principle):** تحقق دارد - Factory فقط مسئولیت ایجاد آبجکت‌ها را دارد و منطق کسب‌وکار در کلاس‌های جداگانه قرار دارد.

**OCP (Open/Closed Principle):** تحقق دارد - برای افزودن نوع جدید، فقط کلاس جدید اضافه می‌شود و کد موجود تغییر نمی‌کند.

**LSP (Liskov Substitution Principle):** تحقق دارد - تمام آبجکت‌های ایجاد شده توسط Factory قابل جایگزینی با interface مشترک هستند.

**ISP (Interface Segregation Principle):** تحقق دارد - Factory از interface‌های کوچک و مخصوص استفاده می‌کند نه interface‌های بزرگ و کلی.

**DIP (Dependency Inversion Principle):** تحقق دارد - Factory به abstractions وابسته است نه concrete classes و dependency injection را تسهیل می‌کند.

### ۵. مفاهیم کلیدی

**کد تمیز:** کدی که خوانا، قابل فهم، ساده و به راحتی قابل نگهداری و توسعه است.

**بدهی فنی:** هزینه‌ای که در آینده باید پرداخت شود به دلیل انتخاب راه‌حل سریع به جای طراحی بهتر در زمان حال.

**بوی بد:** نشانه‌هایی در کد که بیانگر مشکلات عمیق‌تر در طراحی یا پیاده‌سازی هستند.

### ۶. دسته‌بندی بوهای بد کد

**Bloaters (متورم‌کننده‌ها):** کدها، متدها و کلاس‌هایی که بیش از حد بزرگ شده‌اند و کار با آن‌ها دشوار است.

**Object-Orientation Abusers (سوءاستفاده‌کنندگان از شی‌گرایی):** استفاده نادرست یا ناقص از اصول برنامه‌نویسی شی‌گرا.

**Change Preventers (مانع تغییر):** ساختارهایی که تغییر در یک نقطه مستلزم تغییرات متعدد در نقاط دیگر است.

**Dispensables (غیرضروری‌ها):** چیزهایی که وجودشان بی‌معنی است و حذف آن‌ها کد را تمیزتر می‌کند.

**Couplers (جفت‌کننده‌ها):** بوهایی که باعث جفت‌شدگی بیش از حد بین کلاس‌ها یا delegation غیرضروری می‌شوند.

### ۷. Feature Envy

**دسته:** Object-Orientation Abusers (سوءاستفاده‌کنندگان از شی‌گرایی)

**بازآرایی‌های پیشنهادی:** Move Method، Extract Method، Move Field

**موارد نادیده‌گیری:** زمانی که متد با کلاس دیگری بیشتر کار می‌کند اما منطق کسب‌وکار مستلزم قرارگیری آن در کلاس فعلی است.

### ۸. ده بوی بد در پروژه MiniJava

۱. **Long Method** - متد `statusOfMember()` در کلاس `ClassDiagram` (خطوط ۴۵-۶۷)
۲. **Large Class** - interface `LexicalAnalyzer` (کل کلاس)  
۳. **Long Parameter List** - تعریف کلاس `ClassDiagram` (خطوط ۱۲-۱۴)
۴. **Switch Statements** - متد `getTokensOfPhase2Files()` در `LexicalAnalyzer` (خطوط ۱۵۱-۳۳۱)
۵. **Duplicate Code** - بررسی `allClasses.contains()` در کلاس `ClassDiagram`
۶. **Data Class** - کلاس `DependencyEdge` (کل کلاس)
۷. **Comments** - کامنت بی‌فایده در `DiagramGetter.zoomAndRepaint()` (خط ۴۴)
۸. **Dead Code** - متغیر `Graphics graphics` در `DiagramGetter.init()` (خط ۳۳)
۹. **Feature Envy** - متد `equals()` در کلاس `ClassConstructor` (خطوط ۶۰-۶۷)
۱۰. **Message Chains** - زنجیره‌های `getValueType().statusOfMember()` در کلاس `ClassAttribute`

### ۹. پلاگین Formatter

پلاگین formatter کد را به صورت خودکار فرمت می‌کند و استانداردهای کدنویسی را اعمال می‌کند. این پلاگین با یکنواخت کردن فرمت کد، خوانایی را بهبود می‌بخشد و تمرکز بر محتوای کد را تسهیل می‌کند. رابطه آن با بازآرایی کد این است که کد تمیز و منظم اولین قدم برای بازآرایی موثر است و formatter این زمینه را فراهم می‌کند.

## نتیجه‌گیری

بازآرایی‌های انجام شده باعث بهبود کیفیت کد، افزایش خوانایی، کاهش پیچیدگی و بهبود قابلیت نگهداری شده است. استفاده از الگوهای طراحی Strategy و State در فاز اول و تکنیک‌های مختلف بازآرایی در فاز دوم، کد را منعطف‌تر و قابل گسترش‌تر کرده است. این تجربه نشان داد که کاربرد صحیح الگوهای طراحی و بازآرایی مستمر کد، به طور قابل توجهی کیفیت نرم‌افزار را بهبود می‌بخشد.