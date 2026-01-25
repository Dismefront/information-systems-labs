import './Documentation.module.css';

export const Documentation: React.FC = () => {
    return (
        <section className="documentation">
            <div className="class-info">
                <h2>Класс Product</h2>
                <div className="field">
                    <span>Long id:</span> Поле не может быть null, значение должно быть больше 0, уникально, генерируется автоматически
                </div>
                <div className="field">
                    <span>String name:</span> Поле не может быть null, строка не может быть пустой
                </div>
                <div className="field">
                    <span>Coordinates coordinates:</span> Поле не может быть null
                </div>
                <div className="field">
                    <span>ZonedDateTime creationDate:</span> Поле не может быть null, генерируется автоматически
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
                    <span>String partNumber:</span> Длина строки не более 49, поле не может быть null
                </div>
                <div className="field">
                    <span>Person owner:</span> Поле может быть null
                </div>
            </div>

            <div className="class-info">
                <h2>Класс Coordinates</h2>
                <div className="field">
                    <span>int x:</span> Максимальное значение 988
                </div>
                <div className="field">
                    <span>Integer y:</span> Поле не может быть null
                </div>
            </div>

            <div className="class-info">
                <h2>Класс Organization</h2>
                <div className="field">
                    <span>Integer id:</span> Поле не может быть null, значение должно быть больше 0, уникально, генерируется автоматически
                </div>
                <div className="field">
                    <span>String name:</span> Поле не может быть null, строка не может быть пустой
                </div>
                <div className="field">
                    <span>Address officialAddress:</span> Поле не может быть null
                </div>
                <div className="field">
                    <span>int annualTurnover:</span> Значение должно быть больше 0
                </div>
                <div className="field">
                    <span>Long employeesCount:</span> Поле может быть null, значение должно быть больше 0
                </div>
                <div className="field">
                    <span>String fullName:</span> Уникальное значение, поле может быть null
                </div>
                <div className="field">
                    <span>Address postalAddress:</span> Поле не может быть null
                </div>
            </div>

            <div className="class-info">
                <h2>Класс Person</h2>
                <div className="field">
                    <span>String name:</span> Поле не может быть null, строка не может быть пустой
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
                    <span>Long height:</span> Поле может быть null, значение должно быть больше 0
                </div>
                <div className="field">
                    <span>Country nationality:</span> Поле не может быть null
                </div>
            </div>

            <div className="class-info">
                <h2>Класс Address</h2>
                <div className="field">
                    <span>String zipCode:</span> Длина строки не более 13, поле не может быть null
                </div>
                <div className="field">
                    <span>Location town:</span> Поле может быть null
                </div>
            </div>

            <div className="class-info">
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

            <div className="enum-info">
                <h2>Enum UnitOfMeasure</h2>
                <ul>
                    <li>SQUARE_METERS</li>
                    <li>PCS</li>
                    <li>GRAMS</li>
                </ul>
            </div>

            <div className="enum-info">
                <h2>Enum Color</h2>
                <ul>
                    <li>GREEN</li>
                    <li>RED</li>
                    <li>BLACK</li>
                    <li>YELLOW</li>
                </ul>
            </div>

            <div className="enum-info">
                <h2>Enum Country</h2>
                <ul>
                    <li>UNITED_KINGDOM</li>
                    <li>SPAIN</li>
                    <li>ITALY</li>
                    <li>JAPAN</li>
                </ul>
            </div>
        </section>
    );
};