import { API_ENDPOINT } from '@/App';
import { useState } from 'react';

export const UploadProductsFileButton = () => {
    const [yamlContent, setYamlContent] = useState<string | null>(null);
    const handleFileUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (!file) return;
        const reader = new FileReader();
        reader.onload = (e) => {
            setYamlContent(e.target?.result as string);
        };
        reader.readAsText(file);
    };

    const sendToBackend = () => {
        if (!yamlContent) return;
        fetch(`${API_ENDPOINT}/product/upload-data`, {
            method: 'post',
            credentials: 'include',
            headers: {
                'Content-Type': 'text/plain',
            },
            body: yamlContent,
        });
    };
    return (
        <>
            <input type="file" accept=".yaml,.yml" onChange={handleFileUpload} />
            <button onClick={sendToBackend} disabled={!yamlContent}>
                Upload YAML
            </button>
        </>
    );
};
