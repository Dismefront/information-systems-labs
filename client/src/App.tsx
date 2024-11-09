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
                    fetch('http://localhost:8080/protected', {
                        method: 'post',
                        credentials: 'include',
                    }).then();
                }}
            >
                protected
            </button>
        </>
    );
};
