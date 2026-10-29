# Сборка

## GitHub Actions

Workflow: `.github/workflows/build-apk.yml`

- Каждый push в `master` (или `main`) и каждый pull request собирает debug APK.
- Готовый APK лежит в артефактах запуска: вкладка **Actions** → нужный запуск → **Artifacts** → `FossBrowser-debug`.
- Push тега вида `v*` дополнительно публикует APK в **Releases**. Например:

  ```bash
  git tag -a v24-test -m "FOSS Browser 24 test build"
  git push origin v24-test
  ```

- Ручной запуск: **Actions** → **Build APK** → **Run workflow**.

## Подпись

CI подписывает сборки постоянным тестовым ключом, который хранится в секретах репозитория:

| Секрет | Назначение |
| --- | --- |
| `SIGNING_KEYSTORE_BASE64` | PKCS12-keystore в base64 |
| `SIGNING_STORE_PASSWORD` | Пароль хранилища |
| `SIGNING_KEY_ALIAS` | Алиас ключа (`fossbrowser`) |
| `SIGNING_KEY_PASSWORD` | Пароль ключа |

Благодаря постоянному ключу новые сборки устанавливаются поверх предыдущих без удаления приложения.

Если секреты не заданы (например, в pull request из форка), Gradle использует стандартный debug-ключ.

Проверить подпись можно в логе шага **Print APK signing certificate**:

```text
V2 Signer: certificate DN: O=Gypopo118, CN=FossBrowser CI
```

## Локальная сборка

Требования: JDK 21 и Android SDK с платформой 37.

```bash
./gradlew assembleDebug
```

APK появится в `app/build/outputs/apk/debug/`.

## Синхронизация с upstream

Исходный проект: <https://codeberg.org/Gaukler_Faun/FOSS_Browser>

```bash
git fetch upstream
git merge upstream/master
git push origin master
```

## Установка на телефон

1. Скачайте `app-debug.apk` из раздела **Releases**.
2. Разрешите установку из неизвестных источников для браузера или файлового менеджера.
3. Если уже установлена версия FOSS Browser из F-Droid, удалите её: она подписана другим ключом, и Android не позволит обновить её этим APK.

Требуется Android 10 (API 29) или новее.
