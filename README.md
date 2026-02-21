# MovieVault 🎬

Android-додаток для перегляду інформації про фільми: список популярних фільмів, детальна інформація, постери та рейтинги.
Проєкт створюється з навчальною метою для вивчення сучасної Android-розробки та архітурних підходів

# Технології, що використовуються 🛠

- **Kotlin** — основна мова розробки
- **Android SDK**
- **MVVM** — архітектурний патерн
- **ViewModel** — для зберігання стану UI
- **Room** — для роботи з даними
- **Hilt** — для роботи з DI
- **TMDB** — для роботи з API
- **Retrofit / OkHttp** — для роботи з REST API
- **Panging 3** — для пангинації
- **Navigation 3** — для навігіції
- **Coroutines** — асинхронна робота
- **Coil** — завантаження зображень
- **Git / GitHub** — контроль версій

# Features ✨

### Search — пошук фільму по назві 
### Movie Details — детальний опис фільму
### Favorite — додавання фільму в улюбленні 

# Структура проєту 🔧

```text
├── data/
│   ├── remote/
│   │   ├── api/          (ApiService, interceptors)
│   │   └── dto/          (Response DTO models)
│   ├── local/
│   │   ├── db/           (AppDatabase, DAO)
│   │   └── entity/       (Room entities)
│   ├── mapper/           (DTO→Domain, Entity→Domain)
│   └── repository/       (Repository implementations)
├── domain/
│   ├── model/            (Domain models)
│   ├── repository/       (Repository interfaces)
│   └── usecase/          (Use cases)
├── presentation/
│   ├── home/             (HomeScreen, HomeViewModel)
│   ├── search/           (SearchScreen, SearchViewModel)
│   ├── details/          (DetailScreen, DetailViewModel)
│   ├── favorites/        (FavoritesScreen, FavoritesViewModel)
│   ├── components/       (Shared composables)
│   └── navigation/       (NavGraph, Routes)
├── di/                   (Hilt modules)
└── ui/theme/             (Theme, Colors, Typography)
```

# Як зібрати проєкт 🚀

1. Клонувати репозиторій:
   ```bash
   git clone https://github.com/Arteon4647/MovieVault.git
2.Відкрити проєкт в Android Studio

3.Дочекатися завершення синхронізації Gradle

4.Запустити додаток на:
емуляторі Android
або фізичному пристрої

# Статус проєкту 📌

В розробці

Функціонал та архітектура будуть поступово розширюватися.

# Автор 👤

Артем Шарапов

Початківець Android-розробник
