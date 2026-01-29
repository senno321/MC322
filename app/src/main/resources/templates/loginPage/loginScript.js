// Adiciona um listener para o envio do formulário
document.getElementById('loginForm').addEventListener('submit', async function(event) {
    // Previne o comportamento padrão do formulário (que recarregaria a página)
    event.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const errorMessageDiv = document.getElementById('errorMessage');

    // Prepara os dados para enviar para a API
    const loginData = {
        email: email,
        password: password
    };

    try {
        // Faz a requisição POST para o endpoint de login
        const response = await fetch('/api/usuario/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginData)
        });

        // Verifica se a resposta da API foi bem-sucedida
        if (response.ok) {
            console.log('Login bem-sucedido!');
            // Redireciona para a página principal
            window.location.href = '/main'; 
        } else {
            // Se houver erro, exibe a mensagem retornada pela API
            const errorText = await response.text();
            errorMessageDiv.textContent = errorText;
            errorMessageDiv.style.display = 'block';
        }
    } catch (error) {
        // Trata erros de rede ou de conexão
        console.error('Erro ao tentar fazer login:', error);
        errorMessageDiv.textContent = 'Não foi possível conectar ao servidor. Tente novamente mais tarde.';
        errorMessageDiv.style.display = 'block';
    }
});