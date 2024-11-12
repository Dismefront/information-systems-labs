export const App: React.FC = () => {
    return (
        <>
            <button
                onClick={() => {
                    fetch('http://localhost:8080/login', {
                        method: 'POST',
                        body: JSON.stringify({
                            username: 'admin',
                            password: 'admin',
                        }),
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        credentials: 'include',
                    }).then();
                }}
            >
                login
            </button>
            <button
                onClick={() => {
                    fetch('http://localhost:8080/logout', {
                        method: 'post',
                        credentials: 'include',
                    }).then();
                }}
            >
                logout
            </button>
            <button
                onClick={() => {
                    fetch('http://localhost:8080/api/product/create', {
                        method: 'post',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({
                            name: 'productName',
                            coordinatesId: 1,
                            unitOfMeasure: 'GRAMS',
                            manufacturerId: 2,
                            price: 123,
                            rating: 12,
                            partNumber: 999,
                            ownerId: 1,
                            manufactureCost: 123123,
                        }),
                        credentials: 'include',
                    }).then();
                }}
            >
                protected
            </button>
        </>
    );
};
