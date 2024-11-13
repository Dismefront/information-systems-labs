import { Toaster } from 'react-hot-toast';
import './global.css';
import { fetchWrapper } from './shared/fetch-wrapper/fetch-wrapper';
import { NavBar } from './widgets/navbar/NavBar';

export const API_ENDPOINT = `${import.meta.env.VITE_HTTP_SCHEMA}${import.meta.env.VITE_HOST}`;

window.fetch = fetchWrapper;

export const App: React.FC = () => {
    return (
        <>
            <Toaster position="top-right" />
            <NavBar />
            <section>
                <code>
                    <div className="class">
                        <h2>Класс Product</h2>
                        <div className="field">
                            <span>Long id:</span> Поле не может быть null, значение должно быть
                            больше 0, уникально, генерируется автоматически
                        </div>
                        <div className="field">
                            <span>String name:</span> Поле не может быть null, строка не может быть
                            пустой
                        </div>
                        <div className="field">
                            <span>Coordinates coordinates:</span> Поле не может быть null
                        </div>
                        <div className="field">
                            <span>ZonedDateTime creationDate:</span> Поле не может быть null,
                            генерируется автоматически
                        </div>
                        <div className="field">
                            <span>UnitOfMeasure unitOfMeasure:</span> Поле не может быть null
                        </div>
                        <div className="field">
                            <span>Organization manufacturer:</span> Поле не может быть null
                        </div>
                        <div className="field">
                            <span>long price:</span> Значение должно быть больше 0
                        </div>
                        <div className="field">
                            <span>float manufactureCost:</span> Может быть любым числом
                        </div>
                        <div className="field">
                            <span>int rating:</span> Значение должно быть больше 0
                        </div>
                        <div className="field">
                            <span>String partNumber:</span> Длина строки не более 49, поле не может
                            быть null
                        </div>
                        <div className="field">
                            <span>Person owner:</span> Поле может быть null
                        </div>
                    </div>

                    <div className="class">
                        <h2>Класс Coordinates</h2>
                        <div className="field">
                            <span>int x:</span> Максимальное значение 988
                        </div>
                        <div className="field">
                            <span>Integer y:</span> Поле не может быть null
                        </div>
                    </div>

                    <div className="class">
                        <h2>Класс Organization</h2>
                        <div className="field">
                            <span>Integer id:</span> Поле не может быть null, значение должно быть
                            больше 0, уникально, генерируется автоматически
                        </div>
                        <div className="field">
                            <span>String name:</span> Поле не может быть null, строка не может быть
                            пустой
                        </div>
                        <div className="field">
                            <span>Address officialAddress:</span> Поле не может быть null
                        </div>
                        <div className="field">
                            <span>int annualTurnover:</span> Значение должно быть больше 0
                        </div>
                        <div className="field">
                            <span>Long employeesCount:</span> Поле может быть null, значение должно
                            быть больше 0
                        </div>
                        <div className="field">
                            <span>String fullName:</span> Уникальное значение, поле может быть null
                        </div>
                        <div className="field">
                            <span>Address postalAddress:</span> Поле не может быть null
                        </div>
                    </div>

                    <div className="class">
                        <h2>Класс Person</h2>
                        <div className="field">
                            <span>String name:</span> Поле не может быть null, строка не может быть
                            пустой
                        </div>
                        <div className="field">
                            <span>Color eyeColor:</span> Поле не может быть null
                        </div>
                        <div className="field">
                            <span>Color hairColor:</span> Поле может быть null
                        </div>
                        <div className="field">
                            <span>Location location:</span> Поле не может быть null
                        </div>
                        <div className="field">
                            <span>Long height:</span> Поле может быть null, значение должно быть
                            больше 0
                        </div>
                        <div className="field">
                            <span>Country nationality:</span> Поле не может быть null
                        </div>
                    </div>

                    <div className="class">
                        <h2>Класс Address</h2>
                        <div className="field">
                            <span>String zipCode:</span> Длина строки не более 13, поле не может
                            быть null
                        </div>
                        <div className="field">
                            <span>Location town:</span> Поле может быть null
                        </div>
                    </div>

                    <div className="class">
                        <h2>Класс Location</h2>
                        <div className="field">
                            <span>long x:</span> Поле может быть любым числом
                        </div>
                        <div className="field">
                            <span>double y:</span> Поле может быть любым числом
                        </div>
                        <div className="field">
                            <span>Float z:</span> Поле не может быть null
                        </div>
                    </div>

                    <div className="enum">
                        <h2>Enum UnitOfMeasure</h2>
                        <ul>
                            <li>SQUARE_METERS</li>
                            <li>PCS</li>
                            <li>GRAMS</li>
                        </ul>
                    </div>

                    <div className="enum">
                        <h2>Enum Color</h2>
                        <ul>
                            <li>GREEN</li>
                            <li>RED</li>
                            <li>BLACK</li>
                            <li>YELLOW</li>
                        </ul>
                    </div>

                    <div className="enum">
                        <h2>Enum Country</h2>
                        <ul>
                            <li>UNITED_KINGDOM</li>
                            <li>SPAIN</li>
                            <li>ITALY</li>
                            <li>JAPAN</li>
                        </ul>
                    </div>
                </code>
                <h1>Требования к информационной системе</h1>

                <h2>Основное назначение системы</h2>
                <p>
                    Информационная система предназначена для управления объектами, созданными на
                    основе заданного класса.
                </p>

                <h2>Операции с объектами</h2>
                <ul>
                    <li>Создание нового объекта</li>
                    <li>Получение информации об объекте по его ID</li>
                    <li>Обновление объекта (изменение его атрибутов)</li>
                    <li>Удаление объекта</li>
                </ul>
                <p>
                    Каждая операция должна выполняться в отдельном окне приложения. При получении
                    информации об объекте также требуется отображать информацию о связанных с ним
                    объектах.
                </p>
                <p>
                    При создании объекта должна быть возможность связать его с уже существующими
                    объектами вспомогательных классов.
                </p>

                <h2>Хранение данных и синхронизация</h2>
                <ul>
                    <li>
                        Все операции управления объектами выполняются на серверной части и
                        синхронизируются с базой данных.
                    </li>
                    <li>
                        На главном экране отображается таблица со списком объектов с пагинацией,
                        фильтрацией и сортировкой.
                    </li>
                    <li>
                        Изменения в объектах должны автоматически отражаться на интерфейсах других
                        пользователей.
                    </li>
                    <li>Связанные объекты также удаляются при удалении основного объекта.</li>
                </ul>

                <h2>Аутентификация и авторизация пользователей</h2>
                <p>
                    Интерфейс системы должен поддерживать авторизацию и регистрацию пользователей. В
                    системе предусмотрены две роли: обычные пользователи и администраторы.
                </p>
                <ul>
                    <li>
                        Для создания нового администратора требуется одобрение от существующего
                        администратора.
                    </li>
                    <li>
                        Редактирование и удаление объектов могут выполнять только создавшие их
                        пользователи и администраторы.
                    </li>
                    <li>
                        Просматривать объекты могут все пользователи, но редактировать могут только
                        владельцы объектов.
                    </li>
                </ul>

                <h2>Сообщения об ошибках</h2>
                <p>
                    При вводе некорректных значений система должна выдавать пользователю
                    информативные сообщения об ошибках.
                </p>

                <h2>Специальные операции с объектами</h2>
                <ul>
                    <li>
                        Группировка объектов по полю <code>manufacturer</code> и подсчёт количества
                        объектов в каждой группе.
                    </li>
                    <li>
                        Подсчёт объектов с заданным значением поля <code>rating</code>.
                    </li>
                    <li>
                        Подсчёт объектов с <code>partNumber</code> меньше заданного значения.
                    </li>
                    <li>Выбор всех объектов определённого производителя.</li>
                    <li>Снижение цены всех объектов на заданный процент.</li>
                </ul>
                <p>
                    Эти операции должны быть реализованы как функции базы данных и вызываться из
                    бизнес-логики приложения.
                </p>

                <h2>Особенности хранения объектов</h2>
                <ul>
                    <li>Использовать реляционную СУБД PostgreSQL для хранения данных.</li>
                    <li>
                        Для генерации <code>id</code> использовать возможности базы данных.
                    </li>
                    <li>
                        Пароли пользователей должны храниться в зашифрованном виде с использованием
                        алгоритма SHA-384.
                    </li>
                    <li>
                        Хранить информацию о пользователе, который создал объект, и даты обновлений
                        объекта.
                    </li>
                    <li>Структура таблиц должна соответствовать третьей нормальной форме (3НФ).</li>
                </ul>

                <h2>Требования к интерфейсу</h2>
                <ul>
                    <li>Система должна реагировать на некорректный ввод данных.</li>
                    <li>Переходы между частями системы должны осуществляться через меню.</li>
                    <li>
                        В интерфейсах должна отображаться информация о текущем пользователе с
                        возможностью смены пользователя.
                    </li>
                </ul>

                <h2>Опциональное задание</h2>
                <p>
                    В отдельном окне должна быть реализована визуализация объектов с использованием
                    координат и размеров объектов. Объекты от разных пользователей должны
                    отображаться разными цветами. При нажатии на объект должна выводиться информация
                    о нём. Визуализация должна автоматически обновляться при изменении, добавлении
                    или удалении объектов.
                </p>

                <h2>Технологические требования</h2>
                <ul>
                    <li>Использовать Spring MVC в качестве основы для реализации.</li>
                    <li>Для уровня хранения данных использовать JPA + EclipseLink.</li>
                    <li>
                        Разделить приложение на логические части и использовать компоненты для
                        каждого уровня.
                    </li>
                </ul>

                <h2>Содержание отчёта</h2>
                <ul>
                    <li>Текст задания</li>
                    <li>UML-диаграммы классов и пакетов приложения</li>
                    <li>Исходный код или ссылка на репозиторий</li>
                    <li>Выводы по работе</li>
                </ul>

                <h2>Вопросы к защите лабораторной работы</h2>
                <ul>
                    <li>Шаблоны проектирования и архитектурные шаблоны.</li>
                    <li>Платформа Jakarta EE. Виды компонентов.</li>
                    <li>Jakarta EE. Управляемые бины. CDI-бины.</li>
                    <li>Концепция ORM. Библиотеки ORM Hibernate и EclipseLink.</li>
                    <li>
                        Технология Jakarta Persistence. Особенности и интеграция с ORM-провайдерами.
                    </li>
                    <li>Технология Jakarta Data.</li>
                    <li>Платформа Spring и её сходства и различия с Java EE.</li>
                    <li>Spring Boot.</li>
                    <li>Spring Data.</li>
                </ul>
            </section>
        </>
    );
};
