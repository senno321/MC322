document.getElementById('registerForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    const fullname = document.getElementById('fullname').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirm-password').value;
    const errorMessageDiv = document.getElementById('errorMessage');

    // Validação de cliente
    if (password !== confirmPassword) {
        errorMessageDiv.textContent = 'As senhas não coincidem.';
        errorMessageDiv.style.display = 'block';
        return;
    }

    // Os nomes dos campos (nome, email, senha) devem corresponder
    // aos atributos da classe Usuario em Java.
    const userData = {
        nome: fullname,
        email: email,
        senha: password
    };

    try {
        const response = await fetch('/api/usuario/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });

        if (response.status === 201) { // 201 Created
            console.log('Registro bem-sucedido!');
            // Redireciona para a página de login após o sucesso
            window.location.href = '/'; 
        } else {
            const errorText = await response.text();
            errorMessageDiv.textContent = errorText;
            errorMessageDiv.style.display = 'block';
        }
    } catch (error) {
        console.error('Erro ao tentar registrar:', error);
        errorMessageDiv.textContent = 'Não foi possível conectar ao servidor. Tente novamente mais tarde.';
        errorMessageDiv.style.display = 'block';
    }
});