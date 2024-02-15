package Classes.Parser;

import lombok.Getter;
import lombok.Setter;

public record Slot(Slot last, double from, double to, String action) {}
