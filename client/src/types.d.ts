declare module '*.png' {
    const value: any;
    export default value;
}

declare module '*.module.css' {
    const value: any;
    export default value;
}

interface ImportMeta {
    env: {
        VITE_HTTP_SCHEMA: string;
        VITE_HOST: string;
    };
}
