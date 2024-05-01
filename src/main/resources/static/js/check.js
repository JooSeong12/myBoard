function check(){
//    alert("버튼이 눌렸습니다.") //테스트하는 메서드임
    if(document.getElementById("title").value.trim().length == 0){
        alert("제목이 입력되지 않았습니다.")
        document.getElementById("title").focus();
        return false
    }
    if(document.getElementById("contents").value.length == 0){
            alert("내용이 입력되지 않았습니다.")
            document.getElementById("contents").focus();
            return false
        }
    alert("입력이 완료되었습니다.")
    document.getElementById("frm").submit()
    return true
}

function res(){
    alert("처음부터 다시 입력합니다.")
    document.getElementById("frm").reset()
    document.getElementById("title").focus();
}